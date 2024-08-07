package treehouse.server.api.notification.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.member.implementation.MemberQueryAdapter;
import treehouse.server.api.notification.implement.NotificationCommandAdapter;
import treehouse.server.api.notification.implement.NotificationQueryAdapter;
import treehouse.server.api.notification.presentation.dto.NotificationRequestDTO;
import treehouse.server.api.notification.presentation.dto.NotificationResponseDTO;
import treehouse.server.api.treehouse.implementation.TreehouseQueryAdapter;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.notification.Notification;
import treehouse.server.global.entity.treeHouse.TreeHouse;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationCommandAdapter notificationCommandAdapter;
    private final NotificationQueryAdapter notificationQueryAdapter;

    private final MemberQueryAdapter memberQueryAdapter;

    private final TreehouseQueryAdapter treehouseQueryAdapter;

    /**
     * 알림 생성하는 로직
     * @param user
     * @return
     */

    @Transactional
    public void createNotification(User user, Long treehouseId, NotificationRequestDTO.createNotification request, String reactionName) {
        TreeHouse treeHouse = treehouseQueryAdapter.getTreehouseById(treehouseId);
        Member sender = memberQueryAdapter.findByUserAndTreehouse(user, treeHouse);
        Member receiver = memberQueryAdapter.findById(request.getReceiverId());

        Notification notification = NotificationMapper.toNotification(sender, receiver, request, reactionName);
        notificationCommandAdapter.createNotification(notification);
    }

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
                .sorted(Comparator.comparing(Notification::getCreatedAt).reversed()) // createdAt 기준으로 내림차순 정렬
                .toList();

        List<NotificationResponseDTO.getNotification> notificationDtos = notifications.stream()
                .map(notification -> {
                    return NotificationMapper.toGetNotification(notification, user);
                })
                .collect(Collectors.toList());
        return NotificationMapper.toGetNotifications(notificationDtos);
    }

    /**
     * 사용자의 알림을 읽음 처리하는 로직
     * @param user
     * @return
     */
    @Transactional
    public NotificationResponseDTO.readNotification readNotification(User user, Long notificationId){
        Notification notification = notificationQueryAdapter.findById(notificationId);
        notificationCommandAdapter.readNotification(notification);
        return NotificationMapper.toReadNotification(notification);
    }
}
