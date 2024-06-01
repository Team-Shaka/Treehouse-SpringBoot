package treehouse.server.api.reaction.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ReactionResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class reactionSimpleResponseDTO{
        String reactionName;
        Long reactionCount;
        boolean isPushed;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class reactionSimpleListDTO{
        List<reactionSimpleResponseDTO> simpleResponseDTOList;
    }
}
