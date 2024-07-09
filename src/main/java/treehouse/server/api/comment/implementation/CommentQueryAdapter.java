package treehouse.server.api.comment.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import treehouse.server.api.comment.business.CommentMapper;
import treehouse.server.api.comment.persistence.CommentRepository;
import treehouse.server.api.comment.presentation.dto.CommentResponseDTO;
import treehouse.server.api.reaction.business.ReactionMapper;
import treehouse.server.api.reaction.presentation.dto.ReactionResponseDTO;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.comment.Comment;
import treehouse.server.global.entity.comment.CommentType;
import treehouse.server.global.entity.reaction.Reaction;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.ThrowClass.CommentException;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Adapter
@RequiredArgsConstructor
@Slf4j
public class CommentQueryAdapter {

    private final CommentRepository commentRepository;

    public Comment getCommentById(Long commentId){
        return commentRepository.findById(commentId).orElseThrow(()->new CommentException(GlobalErrorCode.COMMENT_NOT_FOUND));
    }

    public List<Comment> getCommentListByPostId(Long postId, Pageable pageable) {
        return commentRepository.findAllByPostId(postId, pageable);
    }

    public List<Comment> getParentCommentListByPostId(Long postId, Pageable pageable) {
        log.error("post id : {}",postId);
        return commentRepository.findAllByPostIdAndType(postId, CommentType.PARENT, pageable);
    }

    public List<Comment> getChildCommentListByPostIdAndParentId(Long postId, Long parentId, Pageable pageable) {
        return commentRepository.findAllByPostIdAndTypeAndParentId(postId, CommentType.CHILD, parentId, pageable);
    }

}

