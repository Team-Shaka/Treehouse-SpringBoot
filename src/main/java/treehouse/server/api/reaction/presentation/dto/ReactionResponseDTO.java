package treehouse.server.api.reaction.presentation.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReactionResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class addReaction {
        private Long reactionId;
        private String reactionName;
        private Long targetId;
        private String targetType;
        private String reactedAt;
    }

}
