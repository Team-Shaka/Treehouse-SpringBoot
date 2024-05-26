package treehouse.server.api.post.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @Getter
    public static class uploadFile{

        @JsonProperty("fileName")
        @Schema(description = "파일 명", example = "사진1")
        @NotBlank(message = "파일 명이 필요합니다.")
        private String fileName;

        @JsonProperty("fileSize")
        @Schema(description = "파일 크기, 바이트 단위", example = "2349")
        @NotNull(message = "fileSize가 필요합니다.")
        private Double fileSize;
    }

    @Getter
    public static class updatePost{

        @JsonProperty("context")
        @Schema(description = "게시글 내용", example = "게시글 내용")
        @NotBlank(message = "게시글 내용이 필요합니다.")
        private String context;
    }
}
