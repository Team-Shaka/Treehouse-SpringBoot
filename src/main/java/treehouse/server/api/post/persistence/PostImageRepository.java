package treehouse.server.api.post.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import treehouse.server.global.entity.post.PostImage;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
