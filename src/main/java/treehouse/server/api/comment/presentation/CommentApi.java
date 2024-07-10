package treehouse.server.api.comment.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import treehouse.server.api.comment.business.CommentService;
import treehouse.server.api.comment.presentation.dto.CommentRequestDTO;
import treehouse.server.api.comment.presentation.dto.CommentResponseDTO;
import treehouse.server.global.common.CommonResponse;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.security.handler.annotation.AuthMember;

@RequiredArgsConstructor
@RestController
@Slf4j
@Tag(name = "📃 Comment API", description = "트리하우스 댓글 관련 API 입니다.")
@RequestMapping("/treehouses/{treehouseId}/feeds/posts/{postId}/comments")
public class CommentApi {

    private final CommentService commentService;

    @Operation(summary = "댓글 신고 API 🔑", description = "댓글을 신고하는 API 입니다.")
    @PostMapping("/{commentId}/reports")
    public CommonResponse reportComment(
            @PathVariable(name = "treehouseId") Long treehouseId,
            @PathVariable(name = "postId") Long postId,
            @PathVariable(name = "commentId") Long commentId,
            @Parameter(hidden = true) @AuthMember User user,
            @RequestBody @Validated CommentRequestDTO.reportComment request
            )
    {
        commentService.reportComment(user,request,treehouseId,postId,commentId);
        return CommonResponse.onSuccess(null);
    }

    @Operation(summary = "댓글 목록 조회 API 🔑 ✅", description = "댓글 목록을 조회하는 API 입니다.")
    @GetMapping()
    public CommonResponse<CommentResponseDTO.CommentListDto> getComments(
            @PathVariable(name = "treehouseId") Long treehouseId,
            @PathVariable(name = "postId") Long postId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @AuthMember @Parameter(hidden = true) User user
    )
    {
        return CommonResponse.onSuccess(commentService.getCommentResponseList(user, treehouseId, postId, page));
    }

    @PostMapping("")
    @Operation(summary = "댓글 작성 API 🔑 ✅", description = "특정 Post에 대해서 댓글을 작성하는 API 입니다.")
    public CommonResponse<CommentResponseDTO.CommentIdResponseDto> createComment(
            @PathVariable(name = "treehouseId")Long treehouseId,
            @PathVariable(name = "postId")Long postId,
            @AuthMember @Parameter(hidden = true) User user,
            @RequestBody CommentRequestDTO.createComment request
            )
    {
        return CommonResponse.onSuccess(commentService.createComment(user, treehouseId, postId, request));
    }

    @PostMapping("/{commentId}")
    @Operation(summary = "대댓글 작성 API 🔑 ✅", description = "특정 Comment에 대해서 대댓글을 작성하는 API 입니다.")
    public CommonResponse<CommentResponseDTO.CommentIdResponseDto> createReply(
            @PathVariable(name = "treehouseId")Long treehouseId,
            @PathVariable(name = "postId")Long postId,
            @PathVariable(name = "commentId")Long commentId,
            @AuthMember @Parameter(hidden = true) User user,
            @RequestBody CommentRequestDTO.createComment request
    )
    {
        return CommonResponse.onSuccess(commentService.createReply(user, treehouseId, postId, commentId, request));
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제 API 🔑 ✅", description = "댓글을 삭제하는 API 입니다.")
    public CommonResponse deleteComment(
            @PathVariable(name = "treehouseId")Long treehouseId,
            @PathVariable(name = "postId")Long postId,
            @PathVariable(name = "commentId")Long commentId,
            @AuthMember @Parameter(hidden = true) User user
    )
    {
        commentService.deleteComment(user,treehouseId,postId, commentId);
        return CommonResponse.onSuccess(null);
    }

    @PostMapping("/{commentId}/reactions")
    @Operation(summary = "댓글 반응 API 🔑 ✅", description = "댓글에 감정표현을 남기는 API 입니다.")
    public CommonResponse<String> reactToComment(
            @PathVariable(name = "treehouseId")Long treehouseId,
            @PathVariable(name = "postId")Long postId,
            @PathVariable(name = "commentId")Long commentId,
            @AuthMember @Parameter(hidden = true) User user,
            @RequestBody CommentRequestDTO.reactToComment request
    )
    {
        return CommonResponse.onSuccess(commentService.reactToComment(user, treehouseId, commentId, request));
    }

}
