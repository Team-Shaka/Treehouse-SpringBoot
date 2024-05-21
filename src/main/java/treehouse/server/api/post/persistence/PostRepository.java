package treehouse.server.api.post.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import treehouse.server.global.entity.post.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
