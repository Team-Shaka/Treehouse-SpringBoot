package treehouse.server.api.treehouse.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import treehouse.server.api.member.business.MemberService;
import treehouse.server.api.treehouse.business.TreeHouseService;
import treehouse.server.api.treehouse.presentation.dto.TreeHouseRequestDTO;
import treehouse.server.api.treehouse.presentation.dto.TreeHouseResponseDTO;
import treehouse.server.global.common.CommonResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "\uD83D\uDCF0 TreeHouse API", description = "트리하우스 관련 API 입니다. 트리에 초대 등의 API가 포함됩니다.")
@RequestMapping("/treehouses")
public class TreeHouseApi {

    private final TreeHouseService treeHouseService;
    private final MemberService memberService;

    @GetMapping("/{treehouseId}")
    @Operation(summary = "트리하우스에 초대하기 API ✅, 🔑", description = "특정 트리하우스에 유저를 전화번호로 초대합니다.")
    public CommonResponse<?> inviteToTreeHouse(@PathVariable(name = "treehouseId") Long treehouseId, @RequestBody TreeHouseRequestDTO.phoneNumRequest request) {
        memberService.checkAlreadyMember(treehouseId, request.getPhoneNum());
        treeHouseService.inviteToTreeHouse(treehouseId, request.getPhoneNum());
        return CommonResponse.onSuccess(null);
    }

}
