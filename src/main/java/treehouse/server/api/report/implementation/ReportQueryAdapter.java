package treehouse.server.api.report.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import treehouse.server.api.report.persistent.ReportRepository;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.comment.Comment;
import treehouse.server.global.entity.post.Post;

@Adapter
@RequiredArgsConstructor
@Slf4j
public class ReportQueryAdapter {
    private final ReportRepository reportRepository;

    public boolean isReportedComment(Comment comment){
        return reportRepository.existsByComment(comment);
    }

    public boolean isReportedPost(Post post) {
        return reportRepository.existsByPost(post);
    }
}
