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
    public static class getWriterProfile {
        private Long memberId;
        private String memberName;
        private String memberProfileImageUrl;
        private Integer memberBranch;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getProfile {
        private Long memberId;
        private String memberName;
        private String userName;
        private Integer closestMemberCount;
        private Integer treehouseCount;
        private Integer fromMe;
        private String profileImageUrl;
        private String bio;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class updateProfile {
        private Long memberId;
    }

}
