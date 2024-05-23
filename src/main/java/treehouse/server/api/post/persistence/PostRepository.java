package treehouse.server.api.post.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import treehouse.server.global.entity.post.Post;
import treehouse.server.global.entity.treeHouse.TreeHouse;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByTreeHouse(TreeHouse treehouse);
}
