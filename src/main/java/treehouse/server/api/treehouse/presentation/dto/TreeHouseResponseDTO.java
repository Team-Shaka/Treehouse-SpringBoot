package treehouse.server.api.treehouse.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TreeHouseResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class treeHouseIdResponse {
        private Long treeHouseId;
    }
}
