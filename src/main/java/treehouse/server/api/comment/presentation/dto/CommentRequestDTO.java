package treehouse.server.api.comment.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class CommentRequestDTO {

    @Getter
    public static class reportComment{

        @JsonProperty("reason")
        @Schema(description = "신고 사유", example = "부적절한 댓글")
        private String reason;

        @JsonProperty("targetMemberId")
        @Schema(description = "신고 대상 멤버 아이디", example = "1")
        @NotNull(message = "신고 대상 멤버 아이디는 필수입니다.")
        private Long targetMemberId;
    }

    @Getter
    public static class createComment{
        // Comment 입력 조건에 따른 valid 조건 추가하기
        private String comment;
    }
}
