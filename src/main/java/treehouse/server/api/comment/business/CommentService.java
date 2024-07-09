package treehouse.server.api.comment.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.comment.implementation.CommentCommandAdapter;
import treehouse.server.api.comment.implementation.CommentQueryAdapter;
import treehouse.server.api.comment.presentation.dto.CommentRequestDTO;
import treehouse.server.api.comment.presentation.dto.CommentResponseDTO;
import treehouse.server.api.member.implementation.MemberQueryAdapter;
import treehouse.server.api.post.implement.PostQueryAdapter;
import treehouse.server.api.reaction.business.ReactionMapper;
import treehouse.server.api.reaction.implementation.ReactionCommandAdapter;
import treehouse.server.api.reaction.implementation.ReactionQueryAdapter;
import treehouse.server.api.reaction.presentation.dto.ReactionResponseDTO;
import treehouse.server.api.report.business.ReportMapper;
import treehouse.server.api.report.implementation.ReportCommandAdapter;
import treehouse.server.api.report.implementation.ReportQueryAdapter;
import treehouse.server.api.treehouse.implementation.TreehouseQueryAdapter;
import treehouse.server.api.user.implement.UserQueryAdapter;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.comment.Comment;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.post.Post;
import treehouse.server.global.entity.reaction.Reaction;
import treehouse.server.global.entity.report.Report;
import treehouse.server.global.entity.treeHouse.TreeHouse;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.ThrowClass.CommentException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommentService {

    private final ReportCommandAdapter reportCommandAdapter;
    private final ReportQueryAdapter reportQueryAdapter;

    private final MemberQueryAdapter memberQueryAdapter;

    private final CommentQueryAdapter commentQueryAdapter;
    private final CommentCommandAdapter commentCommandAdapter;

    private final PostQueryAdapter postQueryAdapter;

    private final TreehouseQueryAdapter treehouseQueryAdapter;
    private final UserQueryAdapter userQueryAdapter;

    private final ReactionCommandAdapter reactionCommandAdapter;
    private final ReactionQueryAdapter reactionQueryAdapter;


    public void reportComment(User user, CommentRequestDTO.reportComment request, Long treehouseId, Long postId, Long commentId){

        TreeHouse treehouse = treehouseQueryAdapter.getTreehouseById(treehouseId);

        Member reporter = memberQueryAdapter.findByUserAndTreehouse(user, treehouse);

        Comment comment = commentQueryAdapter.getCommentById(commentId);

        if (!comment.getPost().getId().equals(postId))
            throw new CommentException(GlobalErrorCode.COMMENT_NOT_FOUND);

        Member target = comment.getWriter();

        if (target.equals(reporter))
            throw new CommentException(GlobalErrorCode.COMMENT_SELF_REPORT);

        Report report = ReportMapper.toCommentReport(request, comment, reporter, target);

        reportCommandAdapter.createReport(report);
    }

    @Transactional(readOnly = true)
    public CommentResponseDTO.CommentListDto getCommentResponseList(User user, Long treehouseId, Long postId, int page) {

        TreeHouse treehouse = treehouseQueryAdapter.getTreehouseById(treehouseId);
        Member member = memberQueryAdapter.findByUserAndTreehouse(user, treehouse);

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
//        List<Comment> commentListByPostId = commentQueryAdapter.getCommentListByPostId(postId, pageable);
        List<Comment> commentListByPostId = commentQueryAdapter.getParentCommentListByPostId(postId, pageable);

        log.error("PO size : {}", commentListByPostId.size());
        // 신고된 댓글을 필터링
        List<Comment> filteredComments = commentListByPostId.stream()
                .filter(comment -> !reportQueryAdapter.isReportedComment(comment))
                .collect(Collectors.toList());

        log.error("size : {}", filteredComments.size());

        // 각 댓글마다 reactionList를 생성
        List<CommentResponseDTO.CommentInfoDto> commentInfoDtoList = filteredComments.stream()
                .map(comment -> {
                    List<Reaction> reactions = reactionQueryAdapter.findAllByComment(comment);
                    Map<String, ReactionResponseDTO.getReaction> reactionMap = reactions.stream()
                            .collect(Collectors.toMap(
                                    Reaction::getReactionName,
                                    reaction -> {
                                        String reactionName = reaction.getReactionName();
                                        Integer reactionCount = reactionQueryAdapter.countReactionsByReactionNameAndCommentId(reactionName, comment.getId());
                                        Boolean isPushed = reactionQueryAdapter.existByMemberAndCommentAndReactionName(member, comment, reactionName);
                                        return ReactionMapper.toGetReaction(reaction, reactionCount, isPushed);
                                    },
                                    (existing, replacement) -> existing // 중복되는 경우 기존 값을 사용
                            ));
                    ReactionResponseDTO.getReactionList reactionDtoList = ReactionMapper.toGetReactionList(reactionMap);

                    // 여기서 comment 에 대한 reply 목록을 조회해서 List<ReplyInfoDto> 를 만든 다음, mapper에 매개변수로 넣고, mapper 코드 업데이트 하자.

                    // 1. queryAdapter 에 parentId 가지고 childList 조회하는 메서드 만들기
                    List<Comment> childCommentList = commentQueryAdapter.getChildCommentListByPostIdAndParentId(postId, comment.getId(), pageable);

                    // 2. mapper 에 childList를 각각 comment 에서 ReplyInfoDto 로 바꾸는 메서드 만들기
                    // 각 답글마다 reactionList를 생성
                    List<CommentResponseDTO.ReplyInfoDto> replyInfoDtoList = childCommentList.stream()
                            .map(reply -> {
                                List<Reaction> replyReactions = reactionQueryAdapter.findAllByComment(reply);
                                Map<String, ReactionResponseDTO.getReaction> replyReactionMap = replyReactions.stream()
                                        .collect(Collectors.toMap(
                                                Reaction::getReactionName,
                                                reaction -> {
                                                    String reactionName = reaction.getReactionName();
                                                    Integer reactionCount = reactionQueryAdapter.countReactionsByReactionNameAndCommentId(reactionName, reply.getId());
                                                    Boolean isPushed = reactionQueryAdapter.existByMemberAndCommentAndReactionName(member, reply, reactionName);
                                                    return ReactionMapper.toGetReaction(reaction, reactionCount, isPushed);
                                                },
                                                (existing, replacement) -> existing // 중복되는 경우 기존 값을 사용
                                        ));
                                ReactionResponseDTO.getReactionList replyReactionDtoList = ReactionMapper.toGetReactionList(replyReactionMap);

                                return CommentMapper.toReplyInfoDto(reply, replyReactionDtoList);
                            })
                            .collect(Collectors.toList());


                    // 3. mapper 에 이렇게 만든 childList 를 포함해서 최종 응답 형태인 CommentList 로 바꾸는 메서드 만들고 호출하기

                    return CommentMapper.toCommentInfoDto(comment, reactionDtoList,replyInfoDtoList);
                })
                .collect(Collectors.toList());

        log.error("commentListDto size : {}", commentListByPostId.size());

        CommentResponseDTO.CommentListDto commentListDto = CommentMapper.toCommentListDto(commentInfoDtoList);
        return commentListDto;
    }

    public CommentResponseDTO.CommentIdResponseDto createComment(User user, Long treehouseId, Long postId, CommentRequestDTO.createComment request){

        TreeHouse treehouse = treehouseQueryAdapter.getTreehouseById(treehouseId);
        Post post = postQueryAdapter.findById(postId);
        Member writer = memberQueryAdapter.findByUserAndTreehouse(user, treehouse);

        Comment comment = CommentMapper.toComment(writer, post, request.getContext());
        Long commentId = commentCommandAdapter.createComment(comment).getId();
        return CommentMapper.toIdResponseDto(commentId);
    }

    public void deleteComment(User user, Long treehouseId, Long postId, Long commentId) {

        Comment comment = commentQueryAdapter.getCommentById(commentId);

        commentCommandAdapter.deleteComment(comment);
    }

    @Transactional
    public String reactToComment(User user, Long treehouseId, Long commentId, CommentRequestDTO.reactToComment request) {
        TreeHouse treehouse = treehouseQueryAdapter.getTreehouseById(treehouseId);
        Member member = memberQueryAdapter.findByUserAndTreehouse(user, treehouse);

        Comment comment = commentQueryAdapter.getCommentById(commentId);

        Boolean isPushed = reactionQueryAdapter.existByMemberAndCommentAndReactionName(member, comment, request.getReactionName());
        if (isPushed) {
            reactionCommandAdapter.deleteCommentReaction(member, comment, request.getReactionName());
            return request.getReactionName() + " is deleted";
        }

        Reaction reaction = ReactionMapper.toCommentReaction(request, comment, member);
        Reaction savedReaction = reactionCommandAdapter.saveReaction(reaction);

        member.addReaction(savedReaction);

        return request.getReactionName() + " is saved";
    }
}
