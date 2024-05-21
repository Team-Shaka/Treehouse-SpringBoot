package treehouse.server.api.feed.presentation.dto;

import lombok.*;
import treehouse.server.api.member.presentation.dto.MemberResponseDTO;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getFeedDetails {

        private MemberResponseDTO.getMember memberProfile;
        private Long postId;
        private String context;
        private List<String> pictureUrlList;
//        private List<ReactionResponseDto.getReaction> reactionList; Reaction 기능 개발 이후 적용
        private String postedAt;
    }
}
