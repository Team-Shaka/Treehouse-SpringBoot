package treehouse.server.api.member.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class MemberRequestDTO {

    @Getter
    public static class registerMember {
        @NotNull(message = "트리하우스 id가 필요합니다.")
        private Long treehouseId;

        @NotBlank(message = "유저 이름이 필요합니다.")
        private String userName;

        @NotBlank(message = "멤버이름(트리하우스에서 사용할 이름)이 필요합니다.")
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
