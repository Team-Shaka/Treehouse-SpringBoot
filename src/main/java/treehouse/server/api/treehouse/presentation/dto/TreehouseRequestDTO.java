package treehouse.server.api.treehouse.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TreehouseRequestDTO {

    @Getter
    public static class createTreehouse{

        @JsonProperty("treehouseName")
        @Schema(description = "트리하우스 이름", example = "Team Shaka")
        @NotBlank(message = "트리하우스 이름이 필요합니다.")
        @Size(min = 2, max = 20, message = "트리하우스 이름은 최소 2자, 최대 20자여야 합니다.")
        private String treehouseName;

        @JsonProperty("treeholeName")
        @Schema(description = "트리 홀 이름", example = "Shaka Treehole")
        @NotBlank(message = "트리 홀 이름이 필요합니다.")
        private String treeholeName;
    }
}
