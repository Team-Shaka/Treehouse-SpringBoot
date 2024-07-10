package treehouse.server.api.member.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import treehouse.server.api.member.business.MemberService;
import treehouse.server.api.member.presentation.dto.MemberRequestDTO;
import treehouse.server.api.member.presentation.dto.MemberResponseDTO;
import treehouse.server.api.post.business.PostService;
import treehouse.server.api.post.presentation.dto.PostResponseDTO;
import treehouse.server.global.common.CommonResponse;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.security.handler.annotation.AuthMember;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "Member API", description = "트리하우스 멤버 관련 API 입니다. 트리하우스 멤버 가입, 탈퇴 등의 API가 포함됩니다.")
public class MemberApi {

    private final MemberService memberService;
    private final PostService postService;

    @PostMapping("/members/register")
    @Operation(summary = "트리하우스 회원가입 \uD83D\uDD11✅", description = "트리하우스 멤버로 가입합니다.")
    public CommonResponse<MemberResponseDTO.registerMember> registerTreehouseMember(
            @RequestBody final MemberRequestDTO.registerMember request,
            @AuthMember @Parameter(hidden = true) User user
    ) {
        return CommonResponse.onSuccess(memberService.register(user, request));
    }

    @GetMapping("/treehouses/{treehouseId}/profiles/myProfile")
    @Operation(summary = "내 프로필 조회 \uD83D\uDC64 ✅", description = "특정 트리하우스에서 내 프로필을 조회합니다.")
    public CommonResponse<MemberResponseDTO.getProfile> getMyProfile(
            @PathVariable final Long treehouseId,
            @AuthMember @Parameter(hidden = true) User user
    ) {
        return CommonResponse.onSuccess(memberService.getMyProfile(user, treehouseId));
    }

    @GetMapping("/treehouses/{treehouseId}/profiles/{memberId}")
    @Operation(summary = "멤버 프로필 조회 \uD83D\uDC64 ✅", description = "특정 트리하우스에서 특정 멤버의 프로필을 조회합니다.")
    public CommonResponse<MemberResponseDTO.getProfile> getMemberProfile(
            @PathVariable(name = "treehouseId") Long treehouseId,
            @PathVariable(name = "memberId") Long memberId,
            @AuthMember @Parameter(hidden = true) User user
    ) {
        return CommonResponse.onSuccess(memberService.getMemberProfile(user, memberId, treehouseId));
    }


    @PatchMapping("/treehouses/{treehouseId}/profiles/myProfile")
    @Operation(summary = "내 프로필 수정 \uD83D\uDC64 ✅", description = "특정 트리하우스에서 내 프로필을 수정합니다.")
    public CommonResponse<MemberResponseDTO.updateProfile> updateProfile(
            @PathVariable final Long treehouseId,
            @RequestBody final MemberRequestDTO.updateProfile request,
            @AuthMember @Parameter(hidden = true) User user
    ) {
        return CommonResponse.onSuccess(memberService.updateProfile(user, treehouseId, request));
    }

    @GetMapping("/treehouses/{treehouseId}/profiles/{memberId}/posts")
    @Operation(summary = "멤버가 작성한 게시글 조회 \uD83D\uDC64 ✅", description = "특정 트리하우스에서 특정 멤버가 작성한 게시글 목록을 조회합니다.")
    public CommonResponse<PostResponseDTO.getMemberPostList> getPosts(
            @PathVariable(name = "treehouseId") Long treehouseId,
            @PathVariable(name = "memberId") Long memberId,
            @RequestParam(defaultValue = "0") int page,
            @AuthMember @Parameter(hidden = true) User user
    ) {
        return CommonResponse.onSuccess(postService.getMemberPosts(user, memberId, treehouseId, page));
    }
}
