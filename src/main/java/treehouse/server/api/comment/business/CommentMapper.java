package treehouse.server.api.comment.business;

import treehouse.server.api.comment.presentation.dto.CommentResponseDTO;
import treehouse.server.api.member.business.MemberMapper;
import treehouse.server.api.reaction.presentation.dto.ReactionResponseDTO;
import treehouse.server.global.common.util.TimeFormatter;
import treehouse.server.global.entity.branch.Branch;
import treehouse.server.global.entity.comment.Comment;
import treehouse.server.global.entity.comment.CommentType;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.post.Post;

import java.util.List;

public class CommentMapper {


    public static CommentResponseDTO.CommentInfoDto toCommentInfoDto(Member member, List<Branch> branches, Comment comment, ReactionResponseDTO.getReactionList reactionList,
                                                                     List<CommentResponseDTO.ReplyInfoDto> replyInfoDtoList) {
        return CommentResponseDTO.CommentInfoDto.builder()
                .commentedAt(TimeFormatter.format(comment.getCreatedAt()))
                .commentId(comment.getId())
                .context(comment.getContent())
                .reactionList(reactionList)
                .replyList(replyInfoDtoList)
                .memberProfile(MemberMapper.toGetWriterProfile(member, comment.getWriter(), branches))
                .build();

    }

    public static CommentResponseDTO.ReplyInfoDto toReplyInfoDto(Member member, List<Branch> branches, Comment comment, ReactionResponseDTO.getReactionList reactionList) {
        return CommentResponseDTO.ReplyInfoDto.builder()
                .commentedAt(TimeFormatter.format(comment.getCreatedAt()))
                .commentId(comment.getId())
                .context(comment.getContent())
                .reactionList(reactionList)
                .memberProfile(MemberMapper.toGetWriterProfile(member, comment.getWriter(), branches))
                .build();

    }

    public static CommentResponseDTO.CommentListDto toCommentListDto(List<CommentResponseDTO.CommentInfoDto> commentInfoDtoList) {
        return CommentResponseDTO.CommentListDto.builder()
                .commentList(commentInfoDtoList)
                .build();
    }

    public static Comment toComment(Member writer, Post post, String content, CommentType type, Long parentId) {
        return Comment.builder()
                .writer(writer)
                .post(post)
                .content(content)
                .type(type)
                .parentId(parentId)
                .build();
    }

    public static CommentResponseDTO.CommentIdResponseDto toIdResponseDto(Long commentId) {
        return CommentResponseDTO.CommentIdResponseDto.builder()
                .commentId(commentId)
                .build();
    }
}
