package treehouse.server.api.branch.presentation.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BranchResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShortestPathResult{
        private int distance;
        private List<Long> path;
    }

    // Node 정보를 담을 클래스
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NodeDTO {
        private Long id;
        private String profileImageUrl;
        private String memberName;

    }

    // Link 정보를 담을 클래스
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LinkDTO {
        private Long sourceId;
        private Long targetId;

    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getMemberBranchView {
        private List<NodeDTO> nodes;
        private List<LinkDTO> links;
        private Long startId; // 시작 노드의 memberId
        private Long endId; // 끝 노드의 memberId
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getCompleteBranchView {
        private List<NodeDTO> nodes;
        private List<LinkDTO> links;
    }
}
