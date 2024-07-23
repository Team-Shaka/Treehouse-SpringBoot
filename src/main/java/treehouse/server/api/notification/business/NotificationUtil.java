package treehouse.server.api.notification.business;

import org.apache.commons.lang3.function.TriFunction;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.notification.NotificationType;

import java.util.EnumMap;
import java.util.Map;

public class NotificationUtil {

    private static final Map<NotificationType, String> titleMap = new EnumMap<>(NotificationType.class);
    private static final Map<NotificationType, TriFunction<Member, Long, String, String>> bodyMap = new EnumMap<>(NotificationType.class);

    static {
        titleMap.put(NotificationType.COMMENT, "댓글 알림");
        titleMap.put(NotificationType.REPLY, "대댓글 알림");
        titleMap.put(NotificationType.INVITATION, "초대 알림");
        titleMap.put(NotificationType.POST_REACTION, "게시글 반응 알림");
        titleMap.put(NotificationType.COMMENT_REACTION, "댓글 반응 알림");

        bodyMap.put(NotificationType.COMMENT, (sender, targetId, reactionName) -> sender.getName() + " 님이 댓글을 남겼습니다.");
        bodyMap.put(NotificationType.REPLY, (sender, targetId, reactionName) -> sender.getName() + " 님이 답글을 남겼습니다.");
        bodyMap.put(NotificationType.INVITATION, (sender, targetId, reactionName) -> sender.getName() + " 님이 트리하우스에 초대했습니다.");
        bodyMap.put(NotificationType.POST_REACTION, (sender, targetId, reactionName) -> sender.getName() + " 님이 게시글에 " + reactionName + "을(를) 눌렀습니다.");
        bodyMap.put(NotificationType.COMMENT_REACTION, (sender, targetId, reactionName) -> sender.getName() + " 님이 댓글에 " + reactionName + "을(를) 눌렀습니다.");
    }

    public static String getTitle(NotificationType type) {
        return titleMap.getOrDefault(type, "Notification");
    }

    public static String getBody(NotificationType type, Member sender, Long targetId, String reactionName) {
        return bodyMap.getOrDefault(type, (s, t, r) -> "알림이 있습니다.").apply(sender, targetId, reactionName);
    }
}
