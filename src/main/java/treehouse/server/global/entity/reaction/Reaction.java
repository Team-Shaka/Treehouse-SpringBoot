package treehouse.server.global.entity.reaction;

import jakarta.persistence.*;
import lombok.*;
import treehouse.server.global.entity.common.BaseDateTimeEntity;
import treehouse.server.global.entity.member.Member;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reaction extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "memberId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String reactionName; // 반응 이름(이모지)

    private Long targetId; // 반응 대상의 ID

    @Enumerated(EnumType.STRING)
    private TargetType targetType;

}
