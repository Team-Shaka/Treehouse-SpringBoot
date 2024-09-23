package treehouse.server.api.user.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


public class UserRequestDTO {

    @Getter
    public static class checkName {
        private String userName;
    }

    @Getter
    public static class registerUser {
        @NotBlank(message = "전화번호를 입력해주세요.")
        private String phoneNumber;
        @NotBlank(message = "유저이름(고유)을 입력해주세요.")
        private String userName;
    }

    @Getter
    public static class loginMember {
        private String phoneNumber;
    }

    @Getter
    public static class reissue {

        @NotNull
        @NotBlank
        private String refreshToken;
    }

    @Getter
    public static class SmsRequestDto {
        @Override
        public String toString() {
            return "SmsRequestDto{" +
                    "targetPhoneNum='" + targetPhoneNum + '\'' +
                    '}';
        }

        private String targetPhoneNum;
    }

    @Getter
    public static class PhoneNumAuthDto {
        @Override
        public String toString() {
            return "PhoneNumAuthDto{" +
                    "phoneNum='" + phoneNum + '\'' +
                    ", authNum=" + authNum +
                    '}';
        }

        private String phoneNum;
        private Integer authNum;
    }

    @Getter
    public static class checkUserStatus {
        private String phoneNumber;
    }

    @Getter
    public static class pushAgreeDto{
        boolean pushAgree;
    }
}
