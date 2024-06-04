package treehouse.server.api.post.persistence;

import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import treehouse.server.global.entity.post.Post;
import treehouse.server.global.entity.treeHouse.TreeHouse;

import java.util.Optional;


public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByTreeHouse(TreeHouse treehouse, Pageable pageable);

    Optional<Post> findByIdWithLock(Long postId, LockModeType lockModeType);
}
