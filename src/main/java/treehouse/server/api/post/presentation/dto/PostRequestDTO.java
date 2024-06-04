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

    @Getter
    public static class reportPost{

        @JsonProperty("reason")
        @Schema(description = "신고 사유", example = "부적절한 게시글")
        @NotBlank(message = "게시글 신고 사유가 필요합니다.")
        private String reason;

        @JsonProperty("targetMemberId")
        @Schema(description = "작성자 멤버 아이디", example = "1")
        @NotNull(message = "작성자 멤버 아이디는 필수입니다.")
        private Long targetMemberId;
    }

    @Getter
    public static class reactToPost {

        @JsonProperty("reactionName")
        @Schema(description = "반응 이름(이모지 기반 유니코드)", example = "👍")
        @NotBlank(message = "반응 종류 또는 이름이 필요합니다.")
        private String reactionName;
    }
}
