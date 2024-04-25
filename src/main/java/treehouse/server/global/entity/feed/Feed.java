package treehouse.server.global.entity.feed;

import jakarta.persistence.*;
import lombok.*;
import treehouse.server.global.entity.comment.Comment;
import treehouse.server.global.entity.common.BaseDateTimeEntity;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.treeHouse.TreeHouse;

import java.util.Comparator;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "writerId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private TreeHouse treeHouse;

    private String content;

    @OneToMany(mappedBy = "feed")
    private List<Comment> commentList;

    @OneToMany(mappedBy = "feed")
    private List<FeedImage> feedImageList;
}