package treehouse.server.api.comment.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.comment.implementation.CommentCommandAdapter;
import treehouse.server.api.comment.implementation.CommentQueryAdapter;
import treehouse.server.api.comment.presentation.dto.CommentRequestDTO;
import treehouse.server.api.comment.presentation.dto.CommentResponseDTO;
import treehouse.server.api.member.implementation.MemberQueryAdapter;
import treehouse.server.api.post.implement.PostQueryAdapter;
import treehouse.server.api.reaction.business.ReactionMapper;
import treehouse.server.api.reaction.implementation.ReactionCommandAdapter;
import treehouse.server.api.reaction.implementation.ReactionQueryAdapter;
import treehouse.server.api.report.business.ReportMapper;
import treehouse.server.api.report.implementation.ReportCommandAdapter;
import treehouse.server.api.report.implementation.ReportQueryAdapter;
import treehouse.server.api.treehouse.implementation.TreehouseQueryAdapter;
import treehouse.server.api.user.implement.UserQueryAdapter;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.comment.Comment;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.post.Post;
import treehouse.server.global.entity.reaction.Reaction;
import treehouse.server.global.entity.report.Report;
import treehouse.server.global.entity.treeHouse.TreeHouse;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.ThrowClass.CommentException;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommentService {

    private final ReportCommandAdapter reportCommandAdapter;
    private final ReportQueryAdapter reportQueryAdapter;

    private final MemberQueryAdapter memberQueryAdapter;

    private final CommentQueryAdapter commentQueryAdapter;
    private final CommentCommandAdapter commentCommandAdapter;

    private final PostQueryAdapter postQueryAdapter;

    private final TreehouseQueryAdapter treehouseQueryAdapter;
    private final UserQueryAdapter userQueryAdapter;

    private final ReactionCommandAdapter reactionCommandAdapter;
    private final ReactionQueryAdapter reactionQueryAdapter;


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

    @Transactional(readOnly = true)
    public CommentResponseDTO.CommentListDto getCommentResponseList(User user, Long postId, int page) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Comment> commentListByPostId = commentQueryAdapter.getCommentListByPostId(postId, pageable);

        for(Comment comment : commentListByPostId){
            if(reportQueryAdapter.isReportedComment(comment)){
                commentListByPostId.remove(comment);
            }
        }
        CommentResponseDTO.CommentListDto commentListDto = CommentMapper.toCommentListDto(commentListByPostId);
        return commentListDto;
    }

    public CommentResponseDTO.CommentIdResponseDto createComment(User user, Long treehouseId, Long postId, CommentRequestDTO.createComment request){

        TreeHouse treehouse = treehouseQueryAdapter.getTreehouseById(treehouseId);
        Post post = postQueryAdapter.findById(postId);
        Member writer = memberQueryAdapter.findByUserAndTreehouse(user, treehouse);

        Comment comment = CommentMapper.toComment(writer, post, request.getComment());
        Long commentId = commentCommandAdapter.createComment(comment).getId();
        return CommentMapper.toIdResponseDto(commentId);
    }

    public void deleteComment(User user, Long treehouseId, Long postId, Long commentId) {

        Comment comment = commentQueryAdapter.getCommentById(commentId);

        commentCommandAdapter.deleteComment(comment);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public String reactToComment(User user, Long treehouseId, Long commentId, CommentRequestDTO.reactToComment request) {
        TreeHouse treehouse = treehouseQueryAdapter.getTreehouseById(treehouseId);
        Member member = memberQueryAdapter.findByUserAndTreehouse(user, treehouse);

        Comment comment = commentQueryAdapter.getCommentById(commentId);

        Boolean isPushed = reactionQueryAdapter.existByMemberAndCommentAndReactionName(member, comment, request.getReactionName());
        if (isPushed) {
            reactionCommandAdapter.deleteCommentReaction(member, comment, request.getReactionName());
            return request.getReactionName() + " is deleted";
        }

        Reaction reaction = ReactionMapper.toCommentReaction(request, comment, member);

        reactionCommandAdapter.saveReaction(reaction);
        return request.getReactionName() + " is saved";
    }
}
