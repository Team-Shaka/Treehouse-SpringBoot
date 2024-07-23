package treehouse.server.api.notification.implement;

import lombok.RequiredArgsConstructor;
import treehouse.server.api.notification.persistence.NotificationRepository;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.notification.Notification;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.ThrowClass.NotificationException;

@Adapter
@RequiredArgsConstructor
public class NotificationQueryAdapter {

    private final NotificationRepository notificationRepository;
    public Notification findById(Long notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationException(GlobalErrorCode.NOTIFICATION_NOT_FOUND));
    }
}
