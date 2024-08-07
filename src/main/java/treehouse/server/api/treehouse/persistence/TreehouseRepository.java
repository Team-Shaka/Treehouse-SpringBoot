package treehouse.server.api.treehouse.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import treehouse.server.global.entity.treeHouse.TreeHouse;

public interface TreehouseRepository extends JpaRepository<TreeHouse, Long> {
    boolean existsByName(String name);
}
