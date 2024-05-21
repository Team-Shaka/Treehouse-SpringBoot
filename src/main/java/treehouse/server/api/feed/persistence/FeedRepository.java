package treehouse.server.api.feed.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import treehouse.server.global.entity.feed.Feed;

public interface FeedRepository extends JpaRepository<Feed, Long> {
}
