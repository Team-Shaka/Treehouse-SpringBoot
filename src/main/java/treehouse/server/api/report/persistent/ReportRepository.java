package treehouse.server.api.report.persistent;

import org.springframework.data.jpa.repository.JpaRepository;
import treehouse.server.global.entity.comment.Comment;
import treehouse.server.global.entity.post.Post;
import treehouse.server.global.entity.report.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

    boolean existsByComment(Comment comment);

    boolean existsByPost(Post post);
}
