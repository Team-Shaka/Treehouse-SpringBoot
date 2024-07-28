package treehouse.server.api.user.presentation.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class checkName {
        private Boolean isDuplicated;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class registerUser {
        private Long userId;
        private String accessToken;
        private String refreshToken;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class reissue {
        private String accessToken;
        private String refreshToken;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class checkPhoneAuth{
        boolean authenticated;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class checkSentSms{
        boolean messageSent;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class checkUserStatus{

        Boolean isNewUser;
        Boolean isInvited;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class loginMember {

        private Long userId;
        private String userName;
        private String profileImageUrl;
        private String accessToken;
        private String refreshToken;
        private List<Long> treehouseIdList;
    }
}
