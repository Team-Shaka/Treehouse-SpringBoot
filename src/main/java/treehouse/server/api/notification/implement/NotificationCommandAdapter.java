package treehouse.server.api.notification.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import treehouse.server.api.notification.persistence.NotificationRepository;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.notification.Notification;

@Adapter
@RequiredArgsConstructor
@Slf4j
public class NotificationCommandAdapter {

    private final NotificationRepository notificationRepository;

    public void createNotification(Notification notification){
        notificationRepository.save(notification);
    }

    public void readNotification(Notification notification) {
        notification.setChecked(true);
        notificationRepository.save(notification);
    }
}
