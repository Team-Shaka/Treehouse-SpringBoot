package treehouse.server.api.reaction.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import treehouse.server.global.entity.reaction.Reaction;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
}
