package treehouse.server.api.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import treehouse.server.api.user.business.UserService;
import treehouse.server.api.user.presentation.dto.UserRequestDTO;
import treehouse.server.api.user.presentation.dto.UserResponseDTO;
import treehouse.server.global.common.CommonResponse;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.fcm.dto.FCMDto;
import treehouse.server.global.fcm.service.FcmService;
import treehouse.server.global.security.handler.annotation.AuthMember;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "😎 User API", description = "글로벌 사용자 API 입니다. 로그인, 회원가입 등의 API가 포함됩니다.")
@RequestMapping("/users")
public class UserApi {

    private final UserService userService;
    private final FcmService fcmService;

    @PostMapping("/checkName")
    @Operation(summary = "아이디 중복 체크 ✅", description = "서비스에서 사용할 유저이름을 중복 체크합니다.")
    public CommonResponse<UserResponseDTO.checkName> checkName(
            @RequestBody final UserRequestDTO.checkName request
    ){
        return CommonResponse.onSuccess(userService.checkName(request));
    }

    @PostMapping("/register")
    @Operation(summary = "회원가입 ✅", description = "회원가입을 진행합니다.")
    public CommonResponse<UserResponseDTO.registerUser> registerMember(
            @RequestBody final UserRequestDTO.registerUser request
    ){
        return CommonResponse.onSuccess(userService.register(request));
    }

    @PostMapping("/reissue")
    @Operation(summary = "토큰 재발급 ✅", description = "토큰을 재발급 합니다.")
    public CommonResponse<UserResponseDTO.reissue> reissue(
        @RequestBody final UserRequestDTO.reissue request
    ){
        return CommonResponse.onSuccess(userService.reissue(request));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인 ✅", description = "휴대폰 번호로 로그인합니다.")
    public CommonResponse<UserResponseDTO.loginMember> login(
             @RequestBody final UserRequestDTO.loginMember request
    ){
        return CommonResponse.onSuccess((userService.login(request)));
    }

    @PostMapping("/phone")
    @Operation(summary = "휴대폰으로 상태 확인", description = "휴대폰 번호로 유저의 상태(신규 유저인지, 초대를 하나라도 받았는지)를 조회합니다.")
    public CommonResponse<UserResponseDTO.checkUserStatus> checkPhoneAuth(
            @RequestBody final UserRequestDTO.checkUserStatus request
    ){
        return CommonResponse.onSuccess(userService.checkUserStatus(request));
    }

    @DeleteMapping("/withdraw")
    @Operation(summary = "회원탈퇴 🔑✅", description = "회원탈퇴를 진행합니다.")
    public CommonResponse<UserResponseDTO.withdraw> withdraw(
            @AuthMember @Parameter(hidden = true) User user
    ){
        fcmService.deleteAllFcmToken(user);
        return CommonResponse.onSuccess(userService.withdraw(user));
    }

    @PostMapping("/push-agree")
    @Operation(summary = "푸시 알림 동의 API 🔑✅️", description = "푸시 알림 동의 API입니다.")
    public CommonResponse<UserResponseDTO.pushAgree> pushAgree(
            @AuthMember @Parameter(hidden = true) User user,
            @RequestBody UserRequestDTO.pushAgreeDto request
    ){
        return CommonResponse.onSuccess(userService.updatePushAgree(user,request));
    }


    @PostMapping("/fcm-token")
    @Operation(summary = "FCM 토큰 저장 API 🔑✅️", description = "FCM 토큰 저장 API입니다.")
    public CommonResponse<UserResponseDTO.saveFcmToken> saveFcmToken(
            @AuthMember @Parameter(hidden = true) User user,
            @RequestBody FCMDto.saveFcmTokenDto request
            ){
        return CommonResponse.onSuccess(fcmService.saveFcmToken(user,request));
    }
}
