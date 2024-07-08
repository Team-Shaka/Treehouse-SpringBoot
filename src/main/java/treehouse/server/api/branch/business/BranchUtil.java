package treehouse.server.api.branch.business;

import treehouse.server.api.branch.presentation.dto.BranchResponseDTO;
import treehouse.server.global.entity.branch.Branch;

import java.util.*;

public class BranchUtil {

    /**
     * 두 멤버 사이의 가장 짧은 거리를 계산하여 정수값으로 니다.
     * @param rootId // 시작 멤버 ID
     * @param leafId // 끝(대상) 멤버 ID
     * @return 두 멤버 사이의 가장 짧은 거리
     */

    public static Integer calculateBranchDegree(List<Branch> branches, Long rootId, Long leafId) {
        // Branch 목록을 사용하여 최단 거리를 계산합니다.
        int shortestDistance = findShortestDistance(branches, rootId, leafId).getDistance();
        return shortestDistance;
    }

    /**
     * BFS 알고리즘을 이용해 두 멤버 사이의 가장 짧은 거리를 계산합니다.
     * @param branches
     * @param startMemberId
     * @param endMemberId
     * @return 두 멤버 사이의 가장 짧은 거리
     */

    public static BranchResponseDTO.ShortestPathResult findShortestDistance(List<Branch> branches, Long startMemberId, Long endMemberId) {
        Map<Long, List<Long>> adjacencyList = new HashMap<>();
        Map<Long, Long> prev = new HashMap<>();
        Set<Long> visited = new HashSet<>();
        Queue<Long> queue = new LinkedList<>();

        // 각 멤버 ID를 기준으로 연결된 Branch를 매핑
        for (Branch branch : branches) {
            adjacencyList.computeIfAbsent(branch.getRoot().getId(), k -> new ArrayList<>()).add(branch.getLeaf().getId());
            adjacencyList.computeIfAbsent(branch.getLeaf().getId(), k -> new ArrayList<>()).add(branch.getRoot().getId());
        }

        queue.add(startMemberId);
        visited.add(startMemberId);
        prev.put(startMemberId, null); // 시작 노드의 선행자는 없음

        while (!queue.isEmpty()) {
            Long current = queue.poll();
            if (current.equals(endMemberId)) {
                break; // 목표 노드에 도달
            }
            for (Long neighbor : adjacencyList.getOrDefault(current, Collections.emptyList())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                    prev.put(neighbor, current);
                }
            }
        }

        // 경로 복원 및 결과 생성
        List<Long> path = new ArrayList<>();
        Long current = endMemberId;
        while (current != null) {
            path.add(current);
            current = prev.get(current);
        }
        Collections.reverse(path); // 경로를 역순으로 뒤집어 정상 순서로 만듦

        int distance = path.size() - 1; // 거리는 경로의 길이에서 1을 뺀 값
        return BranchMapper.toShortestPathResult(distance, path);
    }

    /**
     * 특정 멤버와 가장 가까운 멤버(branchDegree = 1)의 수를 반환합니다.
     * @param memberId
     * @return
     */
    public static Integer countClosestMembers(List<Branch> branches, Long memberId) {
        int count = 0;
        for (Branch branch : branches) {
            if (branch.getRoot().getId().equals(memberId) || branch.getLeaf().getId().equals(memberId)) {
                count++;
            }
        }
        return count;
    }
}
