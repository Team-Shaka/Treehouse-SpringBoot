package treehouse.server.api.post.persistence;

import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.post.Post;
import treehouse.server.global.entity.treeHouse.TreeHouse;

import java.util.List;
import java.util.Optional;


public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByTreeHouse(TreeHouse treehouse, Pageable pageable);

    List<Post> findAllByTreeHouseAndWriter(TreeHouse treeHouse, Member writer, Pageable pageable);
}
