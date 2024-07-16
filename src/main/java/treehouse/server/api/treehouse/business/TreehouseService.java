package treehouse.server.api.treehouse.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.member.implementation.MemberQueryAdapter;
import treehouse.server.api.treehouse.implementation.TreehouseCommandAdapter;
import treehouse.server.api.treehouse.implementation.TreehouseQueryAdapter;
import treehouse.server.api.treehouse.presentation.dto.TreehouseRequestDTO;
import treehouse.server.api.treehouse.presentation.dto.TreehouseResponseDTO;
import treehouse.server.global.entity.treeHouse.TreeHouse;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TreehouseService {

    private final TreehouseCommandAdapter treehouseCommandAdapter;
    private final TreehouseQueryAdapter treehouseQueryAdapter;

    private final MemberQueryAdapter memberQueryAdapter;

    public TreehouseResponseDTO.createTreehouse createTreehouse(TreehouseRequestDTO.createTreehouse request) {

        TreeHouse treehouse = TreehouseMapper.toTreehouse(request);
        TreeHouse savedTreehouse = treehouseCommandAdapter.saveTreehouse(treehouse);

        return TreehouseMapper.toCreateTreehouse(savedTreehouse);
    }

    public TreehouseResponseDTO.getTreehouseDetails getTreehouseDetails(Long treehouseId) {
        TreeHouse treehouse = treehouseQueryAdapter.getTreehouseById(treehouseId);
        return TreehouseMapper.toGetTreehouseDetails(treehouse);
    }


}
