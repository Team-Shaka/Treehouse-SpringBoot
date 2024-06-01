package treehouse.server.api.comment.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import treehouse.server.api.comment.persistence.CommentRepository;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.comment.Comment;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.ThrowClass.CommentException;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

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
}
