package treehouse.server.api.post.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import treehouse.server.api.post.business.PostService;
import treehouse.server.api.post.presentation.dto.PostRequestDTO;
import treehouse.server.api.post.presentation.dto.PostResponseDTO;
import treehouse.server.global.common.CommonResponse;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.security.handler.annotation.AuthMember;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "\uD83D\uDCF0 Feed API", description = "트리하우스 피드 관련 API 입니다. 게시글 작성, 피드 조회 등의 API가 포함됩니다.")
@RequestMapping("/treehouses/{treehouseId}/feeds")
public class PostApi {

    private final PostService postService;

    @GetMapping("/posts/{postId}")
    @Operation(summary = "게시글 상세조회 \uD83D\uDD11✅, 🔑", description = "특정 게시글의 상세정보를 조회합니다.")
    public CommonResponse<PostResponseDTO.getPostDetails> getPostDetails(
            @PathVariable Long treehouseId,
            @PathVariable Long postId,
            @AuthMember @Parameter(hidden = true) User user
    ) {
        return CommonResponse.onSuccess(postService.getPostDetails(user, postId, treehouseId));
    }

    @PostMapping("/posts")
    @Operation(summary = "게시글 작성 \uD83D\uDD11✅ 🔑", description = "게시글 작성 API 입니다.")
    public CommonResponse<PostResponseDTO.createPostResult> createPost(
            @PathVariable(name = "treehouseId") Long treehouseId,
            @RequestBody @Valid PostRequestDTO.createPost request,
            @AuthMember @Parameter(hidden = true) User user
    ) {
        return CommonResponse.onSuccess(postService.createPost(user, request, treehouseId));
    }

    @PostMapping("/posts/images")
    @Operation(summary = "presigned Url 발급 API \uD83D\uDD11✅ 🔑", description = "사진 업로드를 위한 presigned Url을 발급받는 API 입니다.")
    public CommonResponse<PostResponseDTO.createPresignedUrlResult> createPresignedUrl(
            @PathVariable(name = "treehouseId") Long treehouseId,
            @RequestBody @Valid PostRequestDTO.uploadFile request,
            @AuthMember @Parameter(hidden = true) User user
    ) {

        return CommonResponse.onSuccess(postService.createPresignedUrl(request));
    }
    @GetMapping
    @Operation(summary = "게시글 목록 조회 🔑", description = "트리하우스의 게시글 목록을 조회합니다.")
    public CommonResponse<List<PostResponseDTO.getPostDetails>> getPosts(
            @PathVariable Long treehouseId,
            @RequestParam(defaultValue = "0") int page,
            @AuthMember @Parameter(hidden = true) User user
    ){
        return CommonResponse.onSuccess(postService.getPosts(user, treehouseId, page));
    }

    @PatchMapping("/posts/{postId}")
    @Operation(summary = "게시글 수정 ✅ 🔑", description = "게시글을 수정합니다(이미지는 수정불가)")
    public CommonResponse<PostResponseDTO.updatePostResult> updatePost(
            @PathVariable Long treehouseId,
            @PathVariable Long postId,
            @RequestBody @Valid PostRequestDTO.updatePost request,
            @AuthMember @Parameter(hidden = true) User user
    ){
        return CommonResponse.onSuccess(postService.updatePost(user, treehouseId, postId, request));
    }

    @DeleteMapping("/posts/{postId}")
    @Operation(summary = "게시글 삭제 ✅ 🔑", description = "게시글을 삭제합니다.")
    public CommonResponse deletePost(
            @PathVariable Long treehouseId,
            @PathVariable Long postId,
            @AuthMember @Parameter(hidden = true) User user
    ){
        postService.deletePost(user, treehouseId, postId);
        return CommonResponse.onSuccess(null);
    }


    @PostMapping("/posts/{postId}/reports")
    @Operation(summary = "게시글 신고 ✅ 🔑", description = "게시글을 신고합니다.")
    public CommonResponse reportPost(
            @PathVariable(name = "treehouseId") Long treehouseId,
            @PathVariable(name = "postId") Long postId,
            @RequestBody @Validated PostRequestDTO.reportPost request,
            @AuthMember @Parameter(hidden = true) User user
    ){
        postService.reportPost(user,treehouseId,postId,request);
        return CommonResponse.onSuccess(null);
    }

    @PostMapping("/posts/{postId}/reactions")
    @Operation(summary = "게시글 반응 ✅ 🔑", description = "게시글에 감정표현을 남깁니다.")
    public CommonResponse<String> reactToPost(
            @PathVariable(name = "treehouseId") Long treehouseId,
            @PathVariable(name = "postId") Long postId,
            @RequestBody @Validated PostRequestDTO.reactToPost request,
            @AuthMember @Parameter(hidden = true) User user
    ){
        return CommonResponse.onSuccess(postService.reactToPost(user, treehouseId, postId, request));
    }
}
