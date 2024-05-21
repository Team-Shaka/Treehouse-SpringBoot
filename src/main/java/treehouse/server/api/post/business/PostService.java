package treehouse.server.api.post.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.post.implement.PostCommandAdapter;
import treehouse.server.api.post.implement.PostQueryAdapter;
import treehouse.server.api.post.presentation.dto.PostResponseDTO;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.post.Post;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {

    private final PostCommandAdapter postCommandAdapter;
    private final PostQueryAdapter postQueryAdapter;

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
}
