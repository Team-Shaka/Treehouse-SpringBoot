package treehouse.server.api.comment.business;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import treehouse.server.api.comment.presentation.dto.CommentResponseDTO;
import treehouse.server.api.member.business.MemberMapper;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.comment.Comment;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.post.Post;

import java.util.List;
import java.util.stream.Collectors;

public class CommentMapper {


    public static CommentResponseDTO.CommentInfoDto toCommentInfoDto(Comment comment) {
        return CommentResponseDTO.CommentInfoDto.builder()
                .commentedAt(comment.getCreatedAt().toString())
                .commentId(comment.getId())
                .context(comment.getContent())
//                .reactionList(comment.) 리액션 관련 로직 구현 후 추가.
                .memberProfile(MemberMapper.toGetMemberProfile(comment.getWriter()))
                .build();

    }

    public static CommentResponseDTO.CommentListDto toCommentListDto(List<Comment> commentList) {
        List<CommentResponseDTO.CommentInfoDto> commentInfoDtoList = commentList.stream()
                .map(comment ->
                        toCommentInfoDto(comment)).toList();
        return CommentResponseDTO.CommentListDto.builder()
                .commentList(commentInfoDtoList)
                .build();
    }

    public static Comment toComment(Member writer, Post post, String content) {
        return Comment.builder()
                .writer(writer)
                .post(post)
                .content(content)
                .build();
    }

    public static CommentResponseDTO.CommentIdResponseDto toIdResponseDto(Long commentId) {
        return CommentResponseDTO.CommentIdResponseDto.builder()
                .commentId(commentId)
                .build();
    }
}
