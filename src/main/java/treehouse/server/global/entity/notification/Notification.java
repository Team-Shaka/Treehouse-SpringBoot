package treehouse.server.global.entity.notification;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import treehouse.server.global.entity.common.BaseDateTimeEntity;
import treehouse.server.global.entity.member.Member;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String body;
    private boolean isChecked;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @JoinColumn(name = "senderId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member sender;

    @JoinColumn(name = "receiverId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member receiver;

    private Long targetId;
    private LocalDateTime receivedTime;
}