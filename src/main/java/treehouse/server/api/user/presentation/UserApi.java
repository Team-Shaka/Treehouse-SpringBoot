package treehouse.server.api.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import treehouse.server.api.user.business.UserService;
import treehouse.server.api.user.presentation.dto.UserRequestDTO;
import treehouse.server.api.user.presentation.dto.UserResponseDTO;
import treehouse.server.global.common.CommonResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "😎 User API", description = "글로벌 사용자 API 입니다. 로그인, 회원가입 등의 API가 포함됩니다.")
@RequestMapping("/users")
public class UserApi {

    private final UserService userService;

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

    @PostMapping("/login-tmp")
    @Operation(summary = "로그인 임시", description = "로그인 임시.")
    public CommonResponse<UserResponseDTO.registerUser> loginTemp(
             @RequestBody final UserRequestDTO.loginMember request
    ){
        return CommonResponse.onSuccess((userService.login(request)));
    }
}
