package treehouse.server.global.entity.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import treehouse.server.global.entity.Invitation.Invitation;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.comment.Comment;
import treehouse.server.global.entity.common.BaseDateTimeEntity;
import treehouse.server.global.entity.notification.Notification;
import treehouse.server.global.entity.reaction.Reaction;
import treehouse.server.global.entity.treeHouse.TreeHouse;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
public class Member extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; //트리에서 사용할 닉네임

    private String bio; //자기소개

    private String profileImageUrl; //프로필 이미지(트리 별로 상이)

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "treeId")
    @ManyToOne(fetch = FetchType.LAZY)
    private TreeHouse treeHouse;


    @OneToMany(mappedBy = "writer")
    List<Comment> commentList;

    @OneToMany(mappedBy = "sender")
    List<Invitation> invitationList;

    @OneToMany(mappedBy = "member")
    List<Reaction> reactionList;

    public void addReaction(Reaction reaction) {
        reactionList.add(reaction);
    }

    public void updateMember(String name, String bio, String profileImageUrl) {
        this.name = name;
        this.bio = bio;
        this.profileImageUrl = profileImageUrl;
    }
}
