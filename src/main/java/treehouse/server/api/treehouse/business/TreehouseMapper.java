package treehouse.server.api.treehouse.business;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import treehouse.server.api.treehouse.presentation.dto.TreehouseRequestDTO;
import treehouse.server.api.treehouse.presentation.dto.TreehouseResponseDTO;
import treehouse.server.global.entity.treeHouse.TreeHouse;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TreehouseMapper {

    public static TreeHouse toTreehouse(TreehouseRequestDTO.createTreehouse request) {
        return TreeHouse.builder()
                .name(request.getTreehouseName())
                .build();
    }

    public static TreehouseResponseDTO.createTreehouse toCreateTreehouse(TreeHouse treehouse) {
        return TreehouseResponseDTO.createTreehouse.builder()
                .treehouseId(treehouse.getId())
                .build();
    }

    public static TreehouseResponseDTO.getTreehouseDetails toGetTreehouseDetails(TreeHouse treehouse) {
        return TreehouseResponseDTO.getTreehouseDetails.builder()
                .treehouseId(treehouse.getId())
                .treehouseName(treehouse.getName())
                .treehouseSize(treehouse.getMemberList().size())
                .treehouseImageUrl(treehouse.getTreehouseImageUrl())
                .build();
    }

    public static TreehouseResponseDTO.getTreehouses toGetTreehouses(List<TreehouseResponseDTO.getTreehouseDetails> treehouseDtos) {
        return TreehouseResponseDTO.getTreehouses.builder()
                .treehouses(treehouseDtos)
                .build();
    }
}
