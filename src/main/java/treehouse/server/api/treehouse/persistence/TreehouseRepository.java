package treehouse.server.api.treehouse.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import treehouse.server.global.entity.treeHouse.TreeHouse;

import java.util.Optional;

public interface TreehouseRepository extends JpaRepository<TreeHouse, Long> {
    Optional<TreeHouse> findById(Long id);
}
