package treehouse.server.api.branch.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import treehouse.server.api.branch.persistence.BranchRepository;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.branch.Branch;
import treehouse.server.global.entity.treeHouse.TreeHouse;

import java.util.List;

@Adapter
@RequiredArgsConstructor
@Slf4j
public class BranchQueryAdapter {

    private final BranchRepository branchRepository;

    public List<Branch> findAllByTreehouse(TreeHouse treeHouse) {
        return branchRepository.findAllByTreeHouse(treeHouse);
    }
}
