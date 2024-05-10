package treehouse.server.api.notification.presentation.dto;

import lombok.*;
import treehouse.server.global.entity.notification.NotificationType;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getNotification {
        private NotificationType type;
        private String profileImageUrl;
        private String userName;
        private String receivedTime;
        private String treehouseName;
        private Boolean isChecked;
        private Long targetId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getNotifications {
        List<NotificationResponseDTO.getNotification> notifications;
    }
}
