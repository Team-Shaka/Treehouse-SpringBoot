package treehouse.server.api.treehouse.presentation.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TreehouseResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class createTreehouse {

        private Long treehouseId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getTreehouseDetails {

        private Long treehouseId;
        private String treehouseName;
        private Integer treehouseSize;
        private String treehouseImageUrl;
    }
}
