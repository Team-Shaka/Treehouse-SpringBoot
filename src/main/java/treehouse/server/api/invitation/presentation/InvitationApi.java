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
@Tag(name = "📨 Invitation API", description = "초대장 관련 API 입니다. 초대장 조회, 전송 등의 API가 포함됩니다.")
@RequestMapping("/users")
public class InvitationApi {

    private final InvitationService invitationService;

    @GetMapping("/invitation")
    @Operation(summary = "초대장 조회 🔑 ✅", description = "내가 받은 초대장을 조회합니다.")
    public CommonResponse<InvitationResponseDTO.getInvitations> getInvitations(
            @AuthMember @Parameter(hidden = true) User user
    ) {
        return CommonResponse.onSuccess(invitationService.getInvitations(user));
    }

    @GetMapping("/availableInvitation")
    @Operation(summary = "소유한 초대장 개수 및 게이지 조회 🔑 ✅", description = "소유한 초대장 개수 및 게이지를 조회합니다.")
    public CommonResponse<InvitationResponseDTO.myInvitationInfo> getAvailableInvitation(@AuthMember @Parameter(hidden = true) User user){

        return CommonResponse.onSuccess(invitationService.getMyInvitationInfo(user));
    }

    @PostMapping("/invitations/accept")
    @Operation(summary = "초대장을 수락할지 거절할지 결정 🔑 ✅", description = "초대장을 수락할지 거절할지 결정하는 API 입니다.")
    public CommonResponse<InvitationResponseDTO.invitationAccept> acceptInvitation(
            @AuthMember @Parameter(hidden = true) User user, @RequestBody InvitationRequestDTO.invitationAcceptDecision request) {
        return CommonResponse.onSuccess(invitationService.decisionInvitation(user, request));
    }

    @PostMapping("/invitation")
    @Operation(summary = "초대하기 API ✅ 🔑", description = "초대하기 API 입니다. 초대 후 문자나 카카오톡 보내는 것과 별개로 API 호출 부탁드립니닷")
    public CommonResponse<InvitationResponseDTO.createInvitation> createInvitation(
        @AuthMember @Parameter(hidden = true)User user, @RequestBody InvitationRequestDTO.createInvitation request
    ){
        return CommonResponse.onSuccess(invitationService.createInvitation(user,request));
    }

    @DeleteMapping("/invitation/{invitationId}")
    @Operation(summary = "초대장 삭제 API ✅ 🔑", description = "초대장을 삭제하는 API 입니다.")
    public CommonResponse deleteInvitation(
            @PathVariable Long invitationId
    ){
        invitationService.deleteInvitation(invitationId);
        return CommonResponse.onSuccess(null);
    }
}
