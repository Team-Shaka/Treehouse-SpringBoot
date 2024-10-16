package treehouse.server.api.post.implement;

import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import treehouse.server.api.post.persistence.PostRepository;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.post.Post;
import treehouse.server.global.entity.treeHouse.TreeHouse;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.ThrowClass.PostException;

import java.util.List;


@Adapter
@RequiredArgsConstructor
public class PostQueryAdapter {

    private final PostRepository postRepository;

    public Post findById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new PostException(GlobalErrorCode.POST_NOT_FOUND));
    }

    public List<Post> findAllByTreehouse(TreeHouse treehouse, Pageable pageable) {
        return postRepository.findAllByTreeHouse(treehouse, pageable);
    }

    public List<Post> findAllByTreeHouseAndWriter(TreeHouse treeHouse, Member writer, Pageable pageable) {
        return postRepository.findAllByTreeHouseAndWriter(treeHouse, writer, pageable);
    }
}
