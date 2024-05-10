package treehouse.server.api.notification.business;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import treehouse.server.api.notification.presentation.dto.NotificationResponseDTO;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.notification.Notification;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationMapper {

    public static NotificationResponseDTO.getNotification toGetNotification(Notification notification, User user) {
        return NotificationResponseDTO.getNotification.builder()
                .type(notification.getType())
                .profileImageUrl(notification.getSender().getProfileImageUrl())
                .userName(notification.getSender().getName())
                .receivedTime(String.valueOf(notification.getReceivedTime()))
                .treehouseName(notification.getSender().getTreeHouse().getName())
                .isChecked(notification.isChecked())
                .targetId(notification.getTargetId())
                .build();
    }

    public static NotificationResponseDTO.getNotifications toGetNotifications(List<NotificationResponseDTO.getNotification> notificationDtos) {
        return NotificationResponseDTO.getNotifications.builder()
                .notifications(notificationDtos)
                .build();
    }
}
