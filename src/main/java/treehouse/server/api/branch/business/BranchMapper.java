package treehouse.server.api.branch.business;

import treehouse.server.api.branch.presentation.dto.BranchResponseDTO;
import treehouse.server.global.entity.branch.Branch;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.treeHouse.TreeHouse;

import java.util.List;

public class BranchMapper {

    public static Branch toBranch(TreeHouse treeHouse, Member inviter, Member invitee) {
        return Branch.builder()
                .treeHouse(treeHouse)
                .root(inviter)
                .leaf(invitee)
                .branchDegree(1)
                .build();
    }

    public static BranchResponseDTO.ShortestPathResult toShortestPathResult(int distance, List<Long> path) {
        return BranchResponseDTO.ShortestPathResult.builder()
                .distance(distance)
                .path(path)
                .build();
    }

    public static BranchResponseDTO.NodeDTO toNodeDTO(Member member) {
        return BranchResponseDTO.NodeDTO.builder()
                .id(member.getId())
                .profileImageUrl(member.getProfileImageUrl())
                .memberName(member.getName())
                .build();
    }

    public static BranchResponseDTO.LinkDTO toLinkDTO(Long sourceId, Long leafId) {
        return BranchResponseDTO.LinkDTO.builder()
                .sourceId(sourceId)
                .targetId(leafId)
                .build();
    }

    public static BranchResponseDTO.getMemberBranchView toBranchView(List<BranchResponseDTO.NodeDTO> nodes, List<BranchResponseDTO.LinkDTO> links, Long rootId, Long leafId) {
        return BranchResponseDTO.getMemberBranchView.builder()
                .nodes(nodes)
                .links(links)
                .startId(rootId)
                .endId(leafId)
                .build();
    }

    public static BranchResponseDTO.getCompleteBranchView toCompleteBranchView(List<BranchResponseDTO.NodeDTO> nodes, List<BranchResponseDTO.LinkDTO> links) {
        return BranchResponseDTO.getCompleteBranchView.builder()
                .nodes(nodes)
                .links(links)
                .build();
    }
}
