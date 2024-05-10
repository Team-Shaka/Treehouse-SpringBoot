package treehouse.server.api.notification.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.notification.implement.NotificationQueryAdapter;
import treehouse.server.api.notification.presentation.dto.NotificationResponseDTO;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.notification.Notification;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationQueryAdapter notificationQueryAdapter;

    /**
     * 사용자의 알림을 조회하는 로직
     * @param user
     * @return
     */
    @Transactional(readOnly = true)
    public NotificationResponseDTO.getNotifications getNotifications(User user){ //@AuthMember로 들어온 User 객체
        List<Member> members = user.getMemberList(); // 사용자의 Member(가입된 트리하우스 프로필) 목록 조회
        List<Notification> notifications = members.stream()
                .map(Member::getNotificationList)
                .flatMap(List::stream)
                .toList();

        List<NotificationResponseDTO.getNotification> notificationDtos = notifications.stream()
                .map(notification -> {
                    return NotificationMapper.toGetNotification(notification, user);
                })
                .collect(Collectors.toList());
        return NotificationMapper.toGetNotifications(notificationDtos);
    }
}
