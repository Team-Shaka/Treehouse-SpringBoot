package treehouse.server.api.comment.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import treehouse.server.api.member.presentation.dto.MemberResponseDTO;
import treehouse.server.api.reaction.presentation.dto.ReactionResponseDTO;

import java.util.List;

public class CommentResponseDTO {


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentInfoDto{

        MemberResponseDTO.getWriterProfile memberProfile;
        ReactionResponseDTO.getReactionList reactionList;
        Long commentId;
        String context;
        String commentedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentListDto{
        List<CommentInfoDto> commentList;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentIdResponseDto{
        Long commentId;
    }
}
