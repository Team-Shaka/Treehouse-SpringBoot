package treehouse.server.api.post.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import treehouse.server.api.post.business.PostService;
import treehouse.server.api.post.presentation.dto.PostResponseDTO;
import treehouse.server.global.common.CommonResponse;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.security.handler.annotation.AuthMember;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "\uD83D\uDCF0 Feed API", description = "트리하우스 피드 관련 API 입니다. 게시글 작성, 피드 조회 등의 API가 포함됩니다.")
@RequestMapping("/treehouses/{treehouseId}/feeds")
public class PostApi {

    private final PostService postService;

    @GetMapping("/posts/{postId}")
    @Operation(summary = "게시글 상세조회 \uD83D\uDD11✅", description = "특정 게시글의 상세정보를 조회합니다.")
    public CommonResponse<PostResponseDTO.getPostDetails> getFeedDetails(
            @PathVariable Long treehouseId,
            @PathVariable Long postId,
            @AuthMember @Parameter(hidden = true) User user
    ){
        return CommonResponse.onSuccess(postService.getPostDetails(user, postId, treehouseId));
    }
}
