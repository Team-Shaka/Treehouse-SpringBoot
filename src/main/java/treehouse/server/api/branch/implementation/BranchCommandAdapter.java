package treehouse.server.api.branch.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import treehouse.server.api.branch.persistence.BranchRepository;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.branch.Branch;

@Adapter
@RequiredArgsConstructor
@Slf4j
public class BranchCommandAdapter {

    private final BranchRepository branchRepository;

    public void createBranch(Branch branch) {
        branchRepository.save(branch);
    }
}
