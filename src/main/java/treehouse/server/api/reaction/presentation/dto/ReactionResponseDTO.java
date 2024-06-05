package treehouse.server.api.reaction.presentation.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReactionResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getReaction {

        private String reactionName;
        private Integer reactionCount;
        private Boolean isPushed;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getReactionList {
        private List<ReactionResponseDTO.getReaction> reactionList;
    }

}
