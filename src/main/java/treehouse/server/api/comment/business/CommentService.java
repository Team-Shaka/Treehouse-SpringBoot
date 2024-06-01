package treehouse.server.api.comment.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.comment.implementation.CommentQueryAdapter;
import treehouse.server.api.comment.presentation.dto.CommentRequestDTO;
import treehouse.server.api.member.implementation.MemberQueryAdapter;
import treehouse.server.api.post.implement.PostQueryAdapter;
import treehouse.server.api.report.business.ReportMapper;
import treehouse.server.api.report.implementation.ReportCommandAdapter;
import treehouse.server.api.treehouse.implementation.TreehouseQueryAdapter;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.comment.Comment;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.report.Report;
import treehouse.server.global.entity.treeHouse.TreeHouse;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.ThrowClass.CommentException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommentService {

    private final ReportCommandAdapter reportCommandAdapter;

    private final MemberQueryAdapter memberQueryAdapter;

    private final CommentQueryAdapter commentQueryAdapter;

    private final PostQueryAdapter postQueryAdapter;

    private final TreehouseQueryAdapter treehouseQueryAdapter;


    public void reportComment(User user, CommentRequestDTO.reportComment reqeust, Long treehouseId, Long postId, Long commentId){

        TreeHouse treehouse = treehouseQueryAdapter.getTreehouseById(treehouseId);

        Member reporter = memberQueryAdapter.findByUserAndTreehouse(user, treehouse);

        Comment comment = commentQueryAdapter.getCommentById(commentId);

        if (!comment.getPost().getId().equals(postId))
            throw new CommentException(GlobalErrorCode.COMMENT_NOT_FOUND);

        Member target = comment.getWriter();

        if (target.equals(reporter))
            throw new CommentException(GlobalErrorCode.COMMENT_SELF_REPORT);

        Report report = ReportMapper.toCommentReport(reqeust, comment, reporter, target);

        reportCommandAdapter.createReport(report);
    }
}