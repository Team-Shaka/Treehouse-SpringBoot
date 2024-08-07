package treehouse.server.api.treehouse.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import treehouse.server.api.treehouse.business.TreehouseService;
import treehouse.server.api.treehouse.presentation.dto.TreehouseRequestDTO;
import treehouse.server.api.treehouse.presentation.dto.TreehouseResponseDTO;
import treehouse.server.global.common.CommonResponse;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.security.handler.annotation.AuthMember;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = " 🌳 Treehouse API", description = "트리하우스 관련 API 입니다. 트리하우스 생성 등의 API가 포함됩니다.")
@RequestMapping("/treehouses")
public class TreehouseApi {

    private final TreehouseService treehouseService;

    @PostMapping
    @Operation(summary = "트리하우스 생성 🔑", description = "트리하우스를 생성합니다.")
    public CommonResponse<TreehouseResponseDTO.createTreehouse> createTreehouse(
            @Valid @RequestBody TreehouseRequestDTO.createTreehouse request
    ) {
        return CommonResponse.onSuccess(treehouseService.createTreehouse(request));
    }

    @GetMapping("/{treehouseId}")
    @Operation(summary = "트리하우스 조회 🔑", description = "트리하우스 정보를 조회합니다.")
    public CommonResponse<TreehouseResponseDTO.getTreehouseDetails> getTreehouseDetails(
            @PathVariable Long treehouseId
    ) {
        return CommonResponse.onSuccess(treehouseService.getTreehouseDetails(treehouseId));
    }

    @GetMapping
    @Operation(summary = "내 트리하우스 조회 🔑", description = "내가 가입한 트리하우스 목록을 조회합니다.")
    public CommonResponse<TreehouseResponseDTO.getTreehouses> getTreehouses(
            @AuthMember @Parameter(hidden = true) User user
    ) {
        return CommonResponse.onSuccess(treehouseService.getTreehouses(user));
    }

    @GetMapping("/checkName")
    @Operation(summary = "트리하우스 이름 중복 확인 🔑", description = "트리하우스 이름 중복을 확인합니다.")
    public CommonResponse<TreehouseResponseDTO.checkTreehouseName> checkTreehouseName(
            @RequestBody TreehouseRequestDTO.checkTreehouseName request
    ) {
        return CommonResponse.onSuccess(treehouseService.checkTreehouseName(request));
    }
}
