package treehouse.server.api.user.business;

import lombok.*;
import treehouse.server.api.user.presentation.dto.UserResponseDTO;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.User.UserRole;
import treehouse.server.global.entity.User.UserStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {


    public static UserResponseDTO.checkName toCheckNameDTO(boolean isDuplicated){
        return UserResponseDTO.checkName.builder()
                .isDuplicated(isDuplicated)
                .build();
    }

    public static User toUser(String name, String phone){
        return User.builder()
                .name(name)
                .phone(phone)
                .role(UserRole.USER)
                .status(UserStatus.ACTIVE)
                .build();
    }

    public static UserResponseDTO.registerUser toRegister(User user, String accessToken, String refreshToken) {
        return UserResponseDTO.registerUser.builder()
                .userId(user.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static UserResponseDTO.reissue toReissue(String accessToken, String refreshToken) {
        return UserResponseDTO.reissue.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
