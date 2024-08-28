package treehouse.server.api.notification.business;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import treehouse.server.api.notification.presentation.dto.NotificationRequestDTO;
import treehouse.server.api.notification.presentation.dto.NotificationResponseDTO;
import treehouse.server.global.common.util.TimeFormatter;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.notification.Notification;
import treehouse.server.global.entity.notification.NotificationType;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationMapper {

    public static Notification toNotification(Member sender, User receiver, NotificationRequestDTO.createNotification request, String reactionName) {

        return Notification.builder()
                .sender(sender)
                .receiver(receiver)
                .type(request.getType())
                .targetId(request.getTargetId())
                .title(NotificationUtil.getTitle(request.getType()))
                .body(NotificationUtil.getBody(request.getType(), sender, request.getTargetId(), reactionName))
                .receivedTime(LocalDateTime.now())
                .build();
    }

    public static NotificationResponseDTO.getNotification toGetNotification(Notification notification, User user) {
        return NotificationResponseDTO.getNotification.builder()
                .notificationId(notification.getId())
                .type(notification.getType())
                .profileImageUrl(notification.getSender().getProfileImageUrl())
                .userName(notification.getSender().getName())
                .title(notification.getTitle())
                .body(notification.getBody())
                .receivedTime(TimeFormatter.format(notification.getReceivedTime()))
                .treehouseId(notification.getSender().getTreeHouse().getId())
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

    public static NotificationResponseDTO.readNotification toReadNotification(Notification notification) {
        return NotificationResponseDTO.readNotification.builder()
                .notificationId(notification.getId())
                .build();
    }
}
