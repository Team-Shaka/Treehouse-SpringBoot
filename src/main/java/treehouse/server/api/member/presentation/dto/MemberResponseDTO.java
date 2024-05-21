package treehouse.server.api.member.presentation.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class registerMember {
        private Long userId;
        private Long treehouseId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getMemberProfile {
        private Long memberId;
        private String memberName;
        private String memberProfileImageUrl;
        private Integer memberBranch;
    }
}
