package treehouse.server.api.treehouse.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.treehouse.implementation.TreeHouseCommandAdapter;
import treehouse.server.api.treehouse.implementation.TreehouseQueryAdapter;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class TreeHouseService {
    private final TreehouseQueryAdapter treehouseQueryAdapter;
    private final TreeHouseCommandAdapter treeHouseCommandAdapter;


    @Transactional(readOnly = false)
    public void inviteToTreeHouse(Long treeHouseId, String phoneNum){

        // treeHouseId와 phoneNum 으로 member table 에 존재하는지 확인 (이미 존재하는 회원인지)
        // 없다면 새로 저장
        // requestDto에 대한 @Valid 필요 (전화번호 정규표현식 검증용)

    }
}
