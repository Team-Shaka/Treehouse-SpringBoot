package treehouse.server.global.redis.repository;

import org.springframework.data.repository.CrudRepository;
import treehouse.server.global.entity.redis.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
