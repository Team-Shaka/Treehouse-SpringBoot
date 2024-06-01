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
import treehouse.server.global.common.CommonResponse;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.security.handler.annotation.AuthMember;

@RequestMapping("/treehouses/{treehouseId}/feeds/posts/{postId}/comments")
@RequiredArgsConstructor
@RestController
@Slf4j
@Tag(name = "📃 Comment API", description = "트리하우스 댓글 관련 API 입니다.")
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

}
