package treehouse.server.api.comment.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import treehouse.server.global.entity.comment.Comment;
import treehouse.server.global.entity.comment.CommentType;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostId(Long postId, Pageable pageable);

    List<Comment> findAllByPostIdAndType(Long postId, CommentType type, Pageable pageable);

    List<Comment> findAllByPostIdAndTypeAndParentId(Long postId, CommentType type, Long parentId, Pageable pageable);

}
