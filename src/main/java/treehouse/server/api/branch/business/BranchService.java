package treehouse.server.api.branch.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.branch.implementation.BranchCommandAdapter;
import treehouse.server.api.branch.implementation.BranchQueryAdapter;
import treehouse.server.api.branch.presentation.dto.BranchResponseDTO;
import treehouse.server.api.member.implementation.MemberQueryAdapter;
import treehouse.server.api.treehouse.implementation.TreehouseQueryAdapter;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.branch.Branch;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.treeHouse.TreeHouse;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BranchService {

    private final BranchCommandAdapter branchCommandAdapter;
    private final BranchQueryAdapter branchQueryAdapter;
    private final TreehouseQueryAdapter treehouseQueryAdapter;
    private final MemberQueryAdapter memberQueryAdapter;

    @Transactional
    public void createBranch(TreeHouse treeHouse, Member inviter, Member invitee) {
        Branch branch = BranchMapper.toBranch(treeHouse, inviter, invitee);
        branchCommandAdapter.createBranch(branch);
    }

    /**
     * 트리하우스 내 두 멤버 사이의 가장 짧은 거리를 계산하여 브랜치 뷰를 반환합니다.
     * @param user
     * @param treehouseId
     * @param targetMemberId
     * @return BranchResponseDTO.getMemberBranchView
     */

    public BranchResponseDTO.getMemberBranchView getMemberBranchView(User user, Long treehouseId, Long targetMemberId) {

        TreeHouse treeHouse = treehouseQueryAdapter.getTreehouseById(treehouseId);
        List<Branch> branches = branchQueryAdapter.findAllByTreeHouse(treeHouse); // 트리하우스 내 모든 브랜치 조회
        Member member = memberQueryAdapter.findByUserAndTreehouse(user, treeHouse); // 현재 로그인한 트리하우스 멤버
        Long rootId = member.getId(); // 현재 로그인한 트리하우스 멤버의 ID
        BranchResponseDTO.ShortestPathResult shortestPathResult = BranchUtil.findShortestDistance(branches, rootId, targetMemberId); // 최단 거리 계산

        // 최단 거리 결과를 이용해 브랜치 뷰 생성
        // Node 정보 생성
        List<BranchResponseDTO.NodeDTO> nodes = shortestPathResult.getPath().stream()
                .map(memberId -> memberQueryAdapter.findById(memberId))
                .map(BranchMapper::toNodeDTO)
                .collect(Collectors.toList());

        // Link 정보 생성
        List<BranchResponseDTO.LinkDTO> links = new ArrayList<>();
        for (int i = 0; i < shortestPathResult.getPath().size() - 1; i++) {
            links.add(BranchMapper.toLinkDTO(shortestPathResult.getPath().get(i), shortestPathResult.getPath().get(i + 1)));
        }

        // 브랜치 뷰 생성
        return BranchMapper.toBranchView(nodes, links, rootId, targetMemberId);

    }


    public BranchResponseDTO.getCompleteBranchView getCompleteBranchView(User user, Long treehouseId) {
        TreeHouse treeHouse = treehouseQueryAdapter.getTreehouseById(treehouseId);
        List<Branch> branches = branchQueryAdapter.findAllByTreeHouse(treeHouse); // 트리하우스 내 모든 브랜치 조회
        Set<Long> memberIds = new HashSet<>();
        List<BranchResponseDTO.NodeDTO> nodes = new ArrayList<>();
        List<BranchResponseDTO.LinkDTO> links = new ArrayList<>();

        // 모든 멤버 ID를 수집
        for (Branch branch : branches) {
            memberIds.add(branch.getRoot().getId());
            memberIds.add(branch.getLeaf().getId());
            links.add(BranchMapper.toLinkDTO(branch.getRoot().getId(), branch.getLeaf().getId()));
        }

        // 모든 멤버 정보를 조회
        for (Long memberId : memberIds) {
            Member member = memberQueryAdapter.findById(memberId);
            nodes.add(BranchMapper.toNodeDTO(member));
        }

        // 브랜치 뷰 생성
        return BranchMapper.toCompleteBranchView(nodes, links);
    }
}
