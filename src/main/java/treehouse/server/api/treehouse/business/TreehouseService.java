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
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.treeHouse.TreeHouse;

import java.util.List;

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

    public TreehouseResponseDTO.getTreehouses getTreehouses(User user) {
        List<Member> memberList = user.getMemberList();

        List<TreehouseResponseDTO.getTreehouseDetails> treehouseDtos = memberList.stream()
                .map(Member::getTreeHouse)
                .map(TreehouseMapper::toGetTreehouseDetails)
                .toList();

        return TreehouseMapper.toGetTreehouses(treehouseDtos);
    }

}
