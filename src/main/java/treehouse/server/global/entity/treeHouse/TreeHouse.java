package treehouse.server.global.entity.treeHouse;

import jakarta.persistence.*;
import lombok.*;
import treehouse.server.global.entity.Invitation.Invitation;
import treehouse.server.global.entity.common.BaseDateTimeEntity;
import treehouse.server.global.entity.member.Member;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TreeHouse extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "treeHouse")
    private List<Member> memberList;

    @OneToMany(mappedBy = "treeHouse")
    private List<Invitation> invitationList;

    public void addMember(Member member) {
        memberList.add(member);
    }
}
