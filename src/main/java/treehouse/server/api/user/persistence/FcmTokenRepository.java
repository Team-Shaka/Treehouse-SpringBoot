package treehouse.server.api.user.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import treehouse.server.global.entity.User.FcmToken;
import treehouse.server.global.entity.User.User;

import java.util.List;
import java.util.Optional;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
    Optional<FcmToken> findByUser(User user);

    void deleteByUserAndToken(User user, String token);

    void deleteAllByUser(User user);

    void deleteByUser(User user);

    boolean existsByUser(User user);

    boolean existsByUserAndToken(User user, String token);

//    List<FcmToken> findAllByUser(User user);
}
