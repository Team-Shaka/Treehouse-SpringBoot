package treehouse.server.global.entity.report;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import treehouse.server.global.entity.comment.Comment;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.post.Post;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reason;

    @JoinColumn(name = "reporterId")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Member reporterMember;

    @JoinColumn(name = "targetId")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Member targetMember;

    @JoinColumn(name = "postId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @JoinColumn(name = "commentId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    public void setReporterMember(Member reporterMember) {
        this.reporterMember = reporterMember;
    }
}