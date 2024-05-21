package treehouse.server.api.post.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import treehouse.server.api.post.persistence.PostRepository;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.post.Post;

@Adapter
@RequiredArgsConstructor
@Slf4j
public class PostCommandAdapter {

    private final PostRepository postRepository;

    public Post savePost(Post post){
        return postRepository.save(post);
    }

}
