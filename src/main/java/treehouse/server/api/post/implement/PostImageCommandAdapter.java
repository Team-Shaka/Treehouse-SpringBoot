package treehouse.server.api.post.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import treehouse.server.api.post.persistence.PostImageRepository;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.post.PostImage;

import java.util.List;

@Adapter
@RequiredArgsConstructor
@Slf4j
public class PostImageCommandAdapter {

    private final PostImageRepository postImageRepository;

    public void savePostImageList(List<PostImage> postImageList){
        postImageRepository.saveAll(postImageList);
    }
}
