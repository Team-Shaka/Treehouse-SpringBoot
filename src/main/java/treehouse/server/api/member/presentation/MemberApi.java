package treehouse.server.api.member.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import treehouse.server.api.member.business.MemberService;
import treehouse.server.api.member.presentation.dto.MemberRequestDTO;
import treehouse.server.api.member.presentation.dto.MemberResponseDTO;
import treehouse.server.global.common.CommonResponse;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.security.handler.annotation.AuthMember;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "Member API", description = "트리하우스 멤버 관련 API 입니다. 트리하우스 멤버 가입, 탈퇴 등의 API가 포함됩니다.")
@RequestMapping("/members")
public class MemberApi {

    private final MemberService memberService;

    @PostMapping("/register")
    @Operation(summary = "회원가입", description = "트리하우스 멤버로 가입합니다.")
    public CommonResponse<MemberResponseDTO.registerMember> registerTreehouseMember(
            @RequestBody final MemberRequestDTO.registerMember request,
            @AuthMember @Parameter(hidden = true) User user
    ){
        return CommonResponse.onSuccess(memberService.register(user, request));
    }
}
