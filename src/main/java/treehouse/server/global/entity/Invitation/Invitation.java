package treehouse.server.global.entity.Invitation;


import jakarta.persistence.*;
import lombok.*;
import treehouse.server.global.entity.common.BaseDateTimeEntity;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.treeHouse.TreeHouse;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Invitation extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;

    @Setter
    private InvitationStatus status;

    private LocalDateTime expiredAt; //초대장 만료일자

    @JoinColumn(name = "senderId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member sender;
    @JoinColumn(name = "treeId")
    @ManyToOne(fetch = FetchType.LAZY)
    private TreeHouse treeHouse;
}
