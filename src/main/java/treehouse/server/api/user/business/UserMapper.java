package treehouse.server.api.user.business;

import jakarta.annotation.PostConstruct;
import lombok.*;
import org.springframework.stereotype.Component;
import treehouse.server.api.user.presentation.dto.UserResponseDTO;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.User.UserRole;
import treehouse.server.global.entity.User.UserStatus;

import java.util.List;

@Component
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserMapper {

    private final UserService userService;
    private static UserService staticUserService;
    @PostConstruct
    public void init(){
        this.staticUserService = this.userService;
    }

    public static User toUserSecurity(String id){
        return staticUserService.findById(Long.valueOf(id));
    }

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

    public static UserResponseDTO.checkUserStatus toCheckUserStatus(Boolean isNewUser, Boolean isInvited){
        return UserResponseDTO.checkUserStatus.builder()
                .isNewUser(isNewUser)
                .isInvited(isInvited)
                .build();
    }

    public static UserResponseDTO.loginMember toLogin(User user, String accessToken, String refreshToken, List<Long> treehouseIdList){
        return UserResponseDTO.loginMember.builder()
                .userId(user.getId())
                .userName(user.getName())
                .profileImageUrl(user.getProfileImageUrl())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .treehouseIdList(treehouseIdList)
                .build();
    }
}
