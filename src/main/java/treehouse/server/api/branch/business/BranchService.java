package treehouse.server.api.branch.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import treehouse.server.api.branch.implementation.BranchCommandAdapter;
import treehouse.server.api.branch.implementation.BranchQueryAdapter;
import treehouse.server.api.branch.presentation.dto.BranchResponseDTO;
import treehouse.server.api.member.implementation.MemberQueryAdapter;
import treehouse.server.api.treehouse.implementation.TreehouseQueryAdapter;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.branch.Branch;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.treeHouse.TreeHouse;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BranchService {

    private final BranchCommandAdapter branchCommandAdapter;
    private final BranchQueryAdapter branchQueryAdapter;
    private final TreehouseQueryAdapter treehouseQueryAdapter;
    private final MemberQueryAdapter memberQueryAdapter;


    public BranchResponseDTO.getMemberBranchView getMemberBranchView(User user, Long treehouseId, Long targetMemberId) {

        TreeHouse treeHouse = treehouseQueryAdapter.getTreehouseById(treehouseId);
        List<Branch> branches = branchQueryAdapter.findAllByTreehouse(treeHouse); // 트리하우스 내 모든 브랜치 조회
        Member member = memberQueryAdapter.findByUserAndTreehouse(user, treeHouse); // 현재 로그인한 트리하우스 멤버
        Long rootId = member.getId(); // 현재 로그인한 트리하우스 멤버의 ID



    }
}
