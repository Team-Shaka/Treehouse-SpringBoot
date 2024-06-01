package treehouse.server.api.comment.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import treehouse.server.global.entity.comment.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
