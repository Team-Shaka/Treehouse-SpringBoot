package treehouse.server.api.branch.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import treehouse.server.global.entity.branch.Branch;
import treehouse.server.global.entity.treeHouse.TreeHouse;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long>{
    List<Branch> findAllByTreeHouse(TreeHouse treeHouse);
}
