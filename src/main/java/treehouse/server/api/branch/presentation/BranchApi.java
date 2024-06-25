package treehouse.server.api.branch.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import treehouse.server.api.branch.business.BranchService;
import treehouse.server.api.branch.presentation.dto.BranchResponseDTO;
import treehouse.server.global.common.CommonResponse;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.security.handler.annotation.AuthMember;

@RequiredArgsConstructor
@RestController
@Slf4j
@Tag(name = "🔗 Branch API", description = "트리하우스 브랜치 관련 API 입니다.")
@RequestMapping("/treehouses/{treehouseId}/branches")
public class BranchApi {

    private final BranchService branchService;

    @Operation(summary = "두 멤버 간 브랜치 뷰 API 🔑", description = "트리하우스 내 두 멤버 사이의 브랜치 뷰를 반환합니다.")
    @GetMapping
    public CommonResponse<BranchResponseDTO.getMemberBranchView> getMemberBranchView(
            @PathVariable(name = "treehouseId") Long treehouseId,
            @RequestParam(name = "targetMemberId") Long targetMemberId,
            @AuthMember @Parameter(hidden = true) User user
    )
    {
        return CommonResponse.onSuccess(branchService.getMemberBranchView(user, treehouseId, targetMemberId));
    }

    @Operation(summary = "트리하우스 전체 브랜치 뷰 API 🔑", description = "트리하우스 내 모든 멤버 사이의 브랜치 뷰를 반환합니다.")
    @GetMapping("/complete")
    public CommonResponse<BranchResponseDTO.getCompleteBranchView> getCompleteBranchView(
            @PathVariable(name = "treehouseId") Long treehouseId,
            @AuthMember @Parameter(hidden = true) User user
    )
    {
        return CommonResponse.onSuccess(branchService.getCompleteBranchView(user, treehouseId));
    }
}
