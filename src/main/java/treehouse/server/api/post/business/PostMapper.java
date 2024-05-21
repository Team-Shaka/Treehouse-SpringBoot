package treehouse.server.api.post.business;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import treehouse.server.api.post.presentation.dto.PostResponseDTO;
import treehouse.server.api.member.business.MemberMapper;
import treehouse.server.global.common.TimeFormatter;
import treehouse.server.global.entity.post.Post;
import treehouse.server.global.entity.post.PostImage;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostMapper {

    public static PostResponseDTO.getPostDetails toGetPostDetails(Post post) {
        return PostResponseDTO.getPostDetails.builder()
                .memberProfile(MemberMapper.toGetMember(post.getWriter()))
                .postId(post.getId())
                .context(post.getContent())
                .pictureUrlList(post.getPostImageList().stream()
                        .map(PostImage::getImageUrl).toList()
                )
//                .reactionList() // Reaction 기능 개발 이후 수정
                .postedAt(TimeFormatter.format(post.getCreatedAt()))
                .build();
    }
}
