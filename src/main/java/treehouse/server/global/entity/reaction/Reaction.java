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

    @Enumerated(EnumType.STRING)
    private ReactionType type; // 예: LIKE, DISLIKE 등

    private Long targetId; // 반응 대상의 ID

    @Enumerated(EnumType.STRING)
    private TargetType targetType;

}
