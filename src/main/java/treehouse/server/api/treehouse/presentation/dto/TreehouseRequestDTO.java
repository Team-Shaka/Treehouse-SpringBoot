package treehouse.server.api.treehouse.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
        private String treehouseName;

        @JsonProperty("treeholeName")
        @Schema(description = "트리 홀 이름", example = "Shaka Treehole")
        @NotBlank(message = "트리 홀 이름이 필요합니다.")
        private String treeholeName;
    }
}
