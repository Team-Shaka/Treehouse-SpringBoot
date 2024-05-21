package treehouse.server.api.post.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostRequestDTO {

    @Getter
    public static class createPost{

        @JsonProperty("context")
        @Schema(description = "게시글 내용", example = "게시글 내용")
        @NotBlank(message = "게시글 내용이 필요합니다.")
        private String context;

        @JsonProperty("pictureUrlList")
        @Schema(description = "게시글에 업로드 한 사진 url 목록")
        private List<String> pictureUrlList;
    }
}
