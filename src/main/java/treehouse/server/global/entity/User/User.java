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
    @Builder.Default
    private Integer invitationCreatedCount = 0; //초대장 생성 횟수


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

    public void withdraw() {
        this.status = UserStatus.WITHDRAWAL;
        this.inactivatedAt = LocalDateTime.now();
    }

    public void addMember(Member member) {
        memberList.add(member);
    }

    public boolean updatePushAgree(boolean pushAgree){
        this.pushAgree=pushAgree;
        return this.pushAgree;
    }

    public void reduceInvitationCount() {
        this.invitationCount--;
        this.invitationCreatedCount++; // 초대장 생성 횟수 증가
        checkAndIncreaseInvitationCount(); // 3회마다 invitationCount 증가
    }

    // 초대 횟수가 3번째일 때만 invitationCount 1 증가
    private void checkAndIncreaseInvitationCount() {
        if (this.invitationCreatedCount == 3) {
            this.invitationCount++;  // 3번째일 때만 증가
            this.invitationCreatedCount = 0; // 카운트 초기화
        }
    }

    public void increaseActiveRate(int rate) {
        this.activeRate += rate;
        checkAndIncreaseInvitationCountByActiveRate(); // activeRate가 100 단위일 때 invitationCount 증가
    }

    // activeRate가 100 단위일 때 invitationCount 증가
    private void checkAndIncreaseInvitationCountByActiveRate() {
        if (this.activeRate >= 100) {
            int additionalInvitations = this.activeRate / 100; // 100 단위마다 초대장 증가
            this.invitationCount += additionalInvitations; // 초대장 증가
            this.activeRate = this.activeRate % 100; // 남은 activeRate는 100 미만으로 유지
        }
    }
}