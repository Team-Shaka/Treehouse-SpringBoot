package treehouse.server.api.post.business;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.member.implementation.MemberQueryAdapter;
import treehouse.server.api.post.implement.PostCommandAdapter;
import treehouse.server.api.post.implement.PostImageCommandAdapter;
import treehouse.server.api.post.implement.PostQueryAdapter;
import treehouse.server.api.post.presentation.dto.PostRequestDTO;
import treehouse.server.api.post.presentation.dto.PostResponseDTO;
import treehouse.server.api.treehouse.implementation.TreehouseQueryAdapter;
import treehouse.server.global.constants.Consts;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.post.Post;
import treehouse.server.global.entity.post.PostImage;
import treehouse.server.global.entity.treeHouse.TreeHouse;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.ThrowClass.PostException;
import treehouse.server.global.feign.client.PresignedUrlLambdaClient;
import treehouse.server.global.feign.dto.PresignedUrlDTO;

import java.util.List;

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

    private final PresignedUrlLambdaClient presignedUrlLambdaClient;

    /**
     * 게시글 상세조회
     * @param user
     * @param postId
     * @param treehouseId - 게시글 정보에 표시할 memberBranch을 계산하고 감정표현의 isPushed 상태를 반환하기 위해 user와 treehouseId 사용
     * @return PostResponseDTO.getPostDetails
     */
    @Transactional(readOnly = true)
    public PostResponseDTO.getPostDetails getPostDetails(User user, Long postId, Long treehouseId){
        Post post = postQueryAdapter.findById(postId);
        return PostMapper.toGetPostDetails(post);
    }

    public PostResponseDTO.createPostResult createPost(User user, PostRequestDTO.createPost request, Long treehouseId){

        Member member = memberQueryAdapter.getMember(user);

        TreeHouse treehouse = treehouseQueryAdapter.getTreehouseById(treehouseId);

        Post post = PostMapper.toPost(request, member, treehouse);
        List<PostImage> postImageList = PostMapper.toPostImageList(request);
        postImageList.forEach(postImage -> postImage.setPost(post));
        postImageCommandAdapter.savePostImageList(postImageList);
        return PostMapper.toCreatePostResult(postCommandAdapter.savePost(post));
    }

    public PostResponseDTO.createPresignedUrlResult createPresignedUrl(PostRequestDTO.uploadFile request){

        // 사진 크기 체크

        if (request.getFileSize() > Consts.FileSizeLimit.LIMIT.getLimit())
            throw new PostException(GlobalErrorCode.FILE_LIMIT_ERROR);

        // AWS Lambda 호출

        PresignedUrlDTO.PresignedUrlResult result = presignedUrlLambdaClient.getPresignedUrl(request.getFileName());


        return PostMapper.toCreatePresignedUrlResult(result);
    }
}
