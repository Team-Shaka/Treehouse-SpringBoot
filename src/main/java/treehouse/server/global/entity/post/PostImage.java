package treehouse.server.global.entity.post;

import jakarta.persistence.*;
import lombok.*;
import treehouse.server.global.entity.common.BaseDateTimeEntity;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @JoinColumn(name = "postId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public void setPost(Post post){
        if(this.post != null)
            this.post.getPostImageList().remove(this);
        this.post = post;
        post.getPostImageList().add(this);
    }

}