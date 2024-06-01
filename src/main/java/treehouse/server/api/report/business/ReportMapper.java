package treehouse.server.api.report.business;

import treehouse.server.api.comment.presentation.dto.CommentRequestDTO;
import treehouse.server.api.post.presentation.dto.PostRequestDTO;
import treehouse.server.global.entity.comment.Comment;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.post.Post;
import treehouse.server.global.entity.report.Report;

public class ReportMapper {

    public static Report toPostReport(PostRequestDTO.reportPost request, Post post, Member reporter, Member target){
        return Report.builder()
                .reason(request.getReason())
                .post(post)
                .reporterMember(reporter)
                .targetMember(target)
                .build();
    }

    public static Report toCommentReport(CommentRequestDTO.reportComment request, Comment comment, Member reporter, Member target){
        return Report.builder()
                .reason(request.getReason())
                .comment(comment)
                .reporterMember(reporter)
                .targetMember(target)
                .build();
    }
}
