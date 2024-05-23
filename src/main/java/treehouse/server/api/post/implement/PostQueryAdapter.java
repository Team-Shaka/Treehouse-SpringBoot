package treehouse.server.api.post.implement;

import lombok.RequiredArgsConstructor;
import treehouse.server.api.post.persistence.PostRepository;
import treehouse.server.global.annotations.Adapter;
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

    public List<Post> findAllByTreehouse(TreeHouse treehouse) {
        return postRepository.findAllByTreeHouse(treehouse);
    }
}
