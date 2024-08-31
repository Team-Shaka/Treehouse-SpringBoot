package treehouse.server.global.entity.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import org.hibernate.annotations.SQLDelete;
import treehouse.server.global.entity.common.BaseDateTimeEntity;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.notification.Notification;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
@SQLDelete(sql = "UPDATE user SET inactivated_at = NOW(), status = 'WITHDRAWAL' WHERE id = ?")
public class User extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String phone;
    private String name; //고유 문자열 아이디(인스타그램 st.)
    private String bio;

    private String profileImageUrl; //프로필 이미지

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    // 활동량 및 초대장 개수 트리별로 적용되는 것 맞는지 확인하기
    @Builder.Default
    private Integer activeRate = 0; //활동량
    @Builder.Default
    private Integer invitationCount = 3; //남아있는 초대장의 개수


    // 탈퇴일자 필요한지 확인하기
    // 특정 트리의 탈퇴한 멤버를 Member Table 에 그대로 쌓을건지?
    // db의 Member Table에 userId 필드 값은 그대로 두는지 or -1 등으로 갱신하는지
    //  - 만약 특정 User가 특정 Tree에 들어간 적이 있는지 알아야 한다면
    private LocalDateTime inactivatedAt; //탈퇴일자

    @ColumnDefault("false")
    private boolean pushAgree;


    @OneToMany(mappedBy = "user")
    private List<Member> memberList;

    @OneToMany(mappedBy = "receiver")
    List<Notification> notificationList;

    public void addMember(Member member) {
        memberList.add(member);
    }

    public boolean updatePushAgree(boolean pushAgree){
        this.pushAgree=pushAgree;
        return this.pushAgree;
    }

    public void reduceInvitationCount() {
        this.invitationCount--;
    }
}