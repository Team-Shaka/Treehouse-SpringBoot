package treehouse.server.api.post.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import treehouse.server.api.member.presentation.dto.MemberResponseDTO;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getPostDetails {

        private MemberResponseDTO.getMemberProfile memberProfile;
        private Long postId;
        private String context;
        private List<String> pictureUrlList;
        private Integer commentCount;
//        private List<ReactionResponseDto.getReaction> reactionList; Reaction 기능 개발 이후 적용
        private String postedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class createPostResult{

        @Schema(description = "생성 된 포스트 ID", example = "1")
        @NotNull(message = "포스트 아이디는 null이 될 수 없습니다.")
        @JsonProperty("postId")
        private Long postId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class createPresignedUrlResult{

        @Schema(description = "생성된 파일 업로드 경로", example = "url")
        @NotNull(message = "생성된 url은 null이 될 수 없습니다.")
        @JsonProperty("presignedUrl")
        private String uploadUrl;

        @Schema(description = "생성된 파일 접근 경로, 서버로 전달 해주세요", example = "url")
        @NotNull(message = "파일 접근 경로는 null이 될 수 없습니다.")
        @JsonProperty("accessUrl")
        private String accessUrl;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class updatePostResult {

        @Schema(description = "수정 된 포스트 ID", example = "1")
        private Long postId;
    }
}
