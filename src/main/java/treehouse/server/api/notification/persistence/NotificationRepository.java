package treehouse.server.api.notification.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import treehouse.server.global.entity.notification.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
