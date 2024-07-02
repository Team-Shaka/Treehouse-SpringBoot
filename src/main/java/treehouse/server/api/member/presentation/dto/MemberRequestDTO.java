package treehouse.server.api.member.presentation.dto;

import lombok.Getter;

public class MemberRequestDTO {

    @Getter
    public static class registerMember {
        private Long treehouseId;
        private String userName;
        private String memberName;
        private String bio;
        private String profileImageURL;
    }

    @Getter
    public static class updateProfile {
        private String memberName;
        private String bio;
        private String profileImageURL;
    }
}
