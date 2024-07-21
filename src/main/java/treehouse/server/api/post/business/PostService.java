package treehouse.server.api.post.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.branch.implementation.BranchQueryAdapter;
import treehouse.server.api.member.implementation.MemberQueryAdapter;
import treehouse.server.api.notification.business.NotificationService;
import treehouse.server.api.notification.presentation.dto.NotificationRequestDTO;
import treehouse.server.api.post.implement.PostCommandAdapter;
import treehouse.server.api.post.implement.PostImageCommandAdapter;
import treehouse.server.api.post.implement.PostQueryAdapter;
import treehouse.server.api.post.presentation.dto.PostRequestDTO;
import treehouse.server.api.post.presentation.dto.PostResponseDTO;
import treehouse.server.api.reaction.business.ReactionMapper;
import treehouse.server.api.reaction.implementation.ReactionCommandAdapter;
import treehouse.server.api.reaction.implementation.ReactionQueryAdapter;
import treehouse.server.api.reaction.presentation.dto.ReactionResponseDTO;
import treehouse.server.api.report.business.ReportMapper;
import treehouse.server.api.report.implementation.ReportCommandAdapter;
import treehouse.server.api.report.implementation.ReportQueryAdapter;
import treehouse.server.api.treehouse.implementation.TreehouseQueryAdapter;
import treehouse.server.global.constants.Consts;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.branch.Branch;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.notification.NotificationType;
import treehouse.server.global.entity.post.Post;
import treehouse.server.global.entity.post.PostImage;
import treehouse.server.global.entity.reaction.Reaction;
import treehouse.server.global.entity.report.Report;
import treehouse.server.global.entity.treeHouse.TreeHouse;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.ThrowClass.PostException;
import treehouse.server.global.feign.client.PresignedUrlLambdaClient;
import treehouse.server.global.feign.dto.PresignedUrlDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostCommandAdapter postCommandAdapter;
    private final PostQueryAdapter postQueryAdapter;

    private final MemberQueryAdapter memberQueryAdapter;

    private final PostImageCommandAdapter postImageCommandAdapter;

    private final TreehouseQueryAdapter treehouseQueryAdapter;

    private final ReactionCommandAdapter reactionCommandAdapter;

    private final ReportCommandAdapter reportCommandAdapter;

    private final PresignedUrlLambdaClient presignedUrlLambdaClient;

    private final ReactionQueryAdapter reactionQueryAdapter;

    private final ReportQueryAdapter reportQueryAdapter;

    private final BranchQueryAdapter branchQueryAdapter;

    private final NotificationService notificationService;

    /**
     * 게시글 상세조회
     *
     * @param user
     * @param postId
     * @param treehouseId - 게시글 정보에 표시할 memberBranch을 계산하고 감정표현의 isPushed 상태를 반환하기 위해 user와 treehouseId 사용
     * @return PostResponseDTO.getPostDetails
     */
    @Transactional(readOnly = true)
    public PostResponseDTO.getPostDetails getPostDetails(User user, Long postId, Long treehouseId) {

        TreeHouse treehouse = treehouseQueryAdapter.getTreehouseById(treehouseId);
        Member member = memberQueryAdapter.findByUserAndTreehouse(user, treehouse);

        Post post = postQueryAdapter.findById(postId);

        List<PostImage> postImageList = post.getPostImageList();
        List<String> postImageUrlList = postImageList.stream()
                .map(PostImage::getImageUrl)
                .toList();
        List<Reaction> reactions = reactionQueryAdapter.findAllByPost(post); // 게시글에 대한 모든 감정표현 조회
        Map<String, ReactionResponseDTO.getReaction> reactionMap = reactions.stream() // 감정표현을 Map으로 변환
                .collect(Collectors.toMap(
                        Reaction::getReactionName,
                        reaction -> {
                            String reactionName = reaction.getReactionName(); // 감정표현 이름(종류)
                            Integer reactionCount = reactionQueryAdapter.countReactionsByReactionNameAndPostId(reactionName, post.getId()); // 감정표현 카운트
                            Boolean isPushed = reactionQueryAdapter.existByMemberAndPostAndReactionName(member, post, reactionName); // 감정표현이 현재 사용자에 의해 눌렸는지 여부
                            return ReactionMapper.toGetReaction(reaction, reactionCount, isPushed);
                        },
                        (existing, replacement) -> existing // 중복되는 경우 기존 값을 사용
                ));

        ReactionResponseDTO.getReactionList reactionDtoList = ReactionMapper.toGetReactionList(reactionMap);

        List<Branch> branches = branchQueryAdapter.findAllByTreeHouse(treehouse); // 트리하우스 내 모든 브랜치 조회
        return PostMapper.toGetPostDetails(member, branches, post, postImageUrlList, reactionDtoList);
    }

    public PostResponseDTO.createPostResult createPost(User user, PostRequestDTO.createPost request, Long treehouseId) {
        TreeHouse treehouse = treehouseQueryAdapter.getTreehouseById(treehouseId);

        Member member = memberQueryAdapter.findByUserAndTreehouse(user, treehouse);

        Post post = PostMapper.toPost(request, member, treehouse);
        List<PostImage> postImageList = PostMapper.toPostImageList(request);
        postImageList.forEach(postImage -> postImage.setPost(post));
        postImageCommandAdapter.savePostImageList(postImageList);
        return PostMapper.toCreatePostResult(postCommandAdapter.savePost(post));
    }

    public PostResponseDTO.createPresignedUrlResult createPresignedUrl(PostRequestDTO.uploadFile request) {

        // 사진 크기 체크

        if (request.getFileSize() > Consts.FileSizeLimit.LIMIT.getLimit())
            throw new PostException(GlobalErrorCode.FILE_LIMIT_ERROR);

        // AWS Lambda 호출

        PresignedUrlDTO.PresignedUrlResult result = presignedUrlLambdaClient.getPresignedUrl(request.getFileName());


        return PostMapper.toCreatePresignedUrlResult(result);
    }

    /**
     * 게시글 목록 조회
     * @param user
     * @param treehouseId - 게시글 정보에 표시할 memberBranch을 계산하고 감정표현의 isPushed 상태를 반환하기 위해 user와 treehouseId 사용
     * @return List<PostResponseDTO.getPostDetails>
     */
    @Transactional(readOnly = true)
    public List<PostResponseDTO.getPostDetails> getPosts (User user, Long treehouseId, int page){
        // TODO 신고한 게시물과 탈퇴 및 차단한 작성자의 게시물은 제외하는 로직 추가

        TreeHouse treehouse = treehouseQueryAdapter.getTreehouseById(treehouseId);
        Member member = memberQueryAdapter.findByUserAndTreehouse(user, treehouse);
        List<Branch> branches = branchQueryAdapter.findAllByTreeHouse(treehouse);

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Post> postList = postQueryAdapter.findAllByTreehouse(treehouse, pageable);

        List<Post> filteredPosts = postList.stream()
                .filter(post -> !reportQueryAdapter.isReportedPost(post))
                .collect(Collectors.toList());

        List<PostResponseDTO.getPostDetails> postDtoList = filteredPosts.stream()
                .map(post -> {
                    List<PostImage> postImageList = post.getPostImageList();
                    List<String> postImageUrlList= postImageList.stream()
                            .map(PostImage::getImageUrl)
                            .toList();
                    List<Reaction> reactions = reactionQueryAdapter.findAllByPost(post);
                    Map<String, ReactionResponseDTO.getReaction> reactionMap = reactions.stream()
                            .collect(Collectors.toMap(
                                    Reaction::getReactionName,
                                    reaction -> {
                                        String reactionName = reaction.getReactionName();
                                        Integer reactionCount = reactionQueryAdapter.countReactionsByReactionNameAndPostId(reactionName, post.getId());
                                        Boolean isPushed = reactionQueryAdapter.existByMemberAndPostAndReactionName(member, post, reactionName);
                                        return ReactionMapper.toGetReaction(reaction, reactionCount, isPushed);
                                    },
                                    (existing, replacement) -> existing // 중복되는 경우 기존 값을 사용
                            ));

                    ReactionResponseDTO.getReactionList reactionDtoList = ReactionMapper.toGetReactionList(reactionMap);
                    return PostMapper.toGetPostDetails(member, branches, post, postImageUrlList, reactionDtoList);
                })
                .collect(Collectors.toList());

        return postDtoList;
    }

    public PostResponseDTO.getMemberPostList getMemberPosts(User user, Long targetMemberId, Long treehouseId, int page) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        TreeHouse treehouse = treehouseQueryAdapter.getTreehouseById(treehouseId);
        Member member = memberQueryAdapter.findByUserAndTreehouse(user, treehouse);
        Member targetMember = memberQueryAdapter.findById(targetMemberId);
        List<Branch> branches = branchQueryAdapter.findAllByTreeHouse(treehouse);

        List<Post> postListByMember = postQueryAdapter.findAllByTreeHouseAndWriter(treehouse, targetMember, pageable);


        List<PostResponseDTO.getOnlyPostDetail> postDtoList = postListByMember.stream()
                .map(post -> {
                    List<PostImage> postImageList = post.getPostImageList();
                    List<String> postImageUrlList= postImageList.stream()
                            .map(PostImage::getImageUrl)
                            .toList();
                    List<Reaction> reactions = reactionQueryAdapter.findAllByPost(post);
                    Map<String, ReactionResponseDTO.getReaction> reactionMap = reactions.stream()
                            .collect(Collectors.toMap(
                                    Reaction::getReactionName,
                                    reaction -> {
                                        String reactionName = reaction.getReactionName();
                                        Integer reactionCount = reactionQueryAdapter.countReactionsByReactionNameAndPostId(reactionName, post.getId());
                                        Boolean isPushed = reactionQueryAdapter.existByMemberAndPostAndReactionName(member, post, reactionName);
                                        return ReactionMapper.toGetReaction(reaction, reactionCount, isPushed);
                                    },
                                    (existing, replacement) -> existing // 중복되는 경우 기존 값을 사용
                            ));

                    ReactionResponseDTO.getReactionList reactionDtoList = ReactionMapper.toGetReactionList(reactionMap);
                    return PostMapper.toGetOnlyPostDetails(post, postImageUrlList, reactionDtoList);
                })
                .collect(Collectors.toList());

        return PostMapper.toGetMemberPostList(member, targetMember, postDtoList, branches);
    }


    @Transactional
    public PostResponseDTO.updatePostResult updatePost(User user, Long treehouseId, Long postId, PostRequestDTO.updatePost request) {
        //TODO 현재 로그인 한 사용자가 게시글 작성자인지 확인하는 로직 개선
        TreeHouse treehouse = treehouseQueryAdapter.getTreehouseById(treehouseId);
        Member member = memberQueryAdapter.findByUserAndTreehouse(user, treehouse);

        Post post = postQueryAdapter.findById(postId);
        Member writer = post.getWriter();

        if (!member.equals(writer)) {
            throw new PostException(GlobalErrorCode.POST_UNAUTHORIZED);
        }

        post.update(request.getContext());
        return PostMapper.toUpdatePostResult(postCommandAdapter.savePost(post));
    }

    @Transactional
    public void deletePost(User user, Long treehouseId, Long postId) {

        //TODO 현재 로그인 한 사용자가 게시글 작성자인지 확인하는 로직 개선
        TreeHouse treehouse = treehouseQueryAdapter.getTreehouseById(treehouseId);
        Member member = memberQueryAdapter.findByUserAndTreehouse(user, treehouse);

        Post post = postQueryAdapter.findById(postId);
        Member writer = post.getWriter();

        if (!member.equals(writer)) {
            throw new PostException(GlobalErrorCode.POST_UNAUTHORIZED);
        }

        postCommandAdapter.deletePost(post);
    }

    /**
     * 게시글 신고하기
     * @param user
     * @param treehouseId
     * @param postId
     * @param request
     */
    @Transactional
    public void reportPost(User user, Long treehouseId, Long postId,PostRequestDTO.reportPost request) {

        TreeHouse treehouse = treehouseQueryAdapter.getTreehouseById(treehouseId);
        Member reporter = memberQueryAdapter.findByUserAndTreehouse(user, treehouse);

        Post post = postQueryAdapter.findById(postId);

        Member target = post.getWriter();

        if (reporter.equals(target))
            throw new PostException(GlobalErrorCode.POST_SELF_REPORT);

        Report report = ReportMapper.toPostReport(request, post, reporter, target);

        reportCommandAdapter.createReport(report);
    }

    @Transactional
    public String reactToPost(User user, Long treehouseId, Long postId, PostRequestDTO.reactToPost request) {
        TreeHouse treehouse = treehouseQueryAdapter.getTreehouseById(treehouseId);
        Member member = memberQueryAdapter.findByUserAndTreehouse(user, treehouse);

        Post post = postQueryAdapter.findById(postId);

        Boolean isPushed = reactionQueryAdapter.existByMemberAndPostAndReactionName(member, post, request.getReactionName());
        if (isPushed) {
            reactionCommandAdapter.deletePostReaction(member, post, request.getReactionName());
            return request.getReactionName() + " is deleted";
        }

        Reaction reaction = ReactionMapper.toPostReaction(request, post, member);
        Reaction savedReaction = reactionCommandAdapter.saveReaction(reaction);

        member.addReaction(savedReaction);

        //알림 생성
        NotificationRequestDTO.createNotification notificationRequest = new NotificationRequestDTO.createNotification();
        notificationRequest.setReceiverId(post.getWriter().getId()); // 여기서 receiver 설정 (예시)
        notificationRequest.setTargetId(post.getId());
        notificationRequest.setType(NotificationType.POST_REACTION); // 알림 타입 설정 (예시)
        notificationService.createNotification(user, treehouseId, notificationRequest, savedReaction.getReactionName());

        return request.getReactionName() + " is saved";
    }
}
