package treehouse.server.api.invitation.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import treehouse.server.api.invitation.business.InvitationService;
import treehouse.server.api.invitation.presentation.dto.InvitationRequestDTO;
import treehouse.server.api.invitation.presentation.dto.InvitationResponseDTO;
import treehouse.server.global.common.CommonResponse;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.security.handler.annotation.AuthMember;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "😎 Invitation API", description = "초대장 관련 API 입니다. 초대장 조회, 전송 등의 API가 포함됩니다.")
@RequestMapping("/invi")
public class InvitationApi {

    private final InvitationService invitationService;

    @GetMapping("/invitation")
    @Operation(summary = "초대장 조회", description = "내가 받은 초대장을 조회합니다.")
    public CommonResponse<InvitationResponseDTO.getInvitations> getInvitations(
            @AuthMember @Parameter(hidden = true) User user
    ) {
        return CommonResponse.onSuccess(invitationService.getInvitations(user));
    }


}
