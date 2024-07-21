package treehouse.server.api.notification.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import treehouse.server.api.notification.business.NotificationService;
import treehouse.server.global.entity.notification.NotificationType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationRequestDTO {

    @Getter
    @Setter
    public static class createNotification {

        private String title;
        private String body;
        private NotificationType type;
        private Long targetId;
        private Long receiverId;
    }
}
