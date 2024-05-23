package treehouse.server.api.post.business;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import treehouse.server.api.post.presentation.dto.PostRequestDTO;
import treehouse.server.api.post.presentation.dto.PostResponseDTO;
import treehouse.server.api.member.business.MemberMapper;
import treehouse.server.global.common.TimeFormatter;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.post.Post;
import treehouse.server.global.entity.post.PostImage;
import treehouse.server.global.entity.treeHouse.TreeHouse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostMapper {

    public static PostResponseDTO.getPostDetails toGetPostDetails(Post post) {
        return PostResponseDTO.getPostDetails.builder()
                .memberProfile(MemberMapper.toGetMemberProfile(post.getWriter()))
                .postId(post.getId())
                .context(post.getContent())
                .pictureUrlList(post.getPostImageList().stream()
                        .map(PostImage::getImageUrl).toList()
                )
                .commentCount(post.getCommentList().size())
//                .reactionList() // Reaction 기능 개발 이후 수정
                .postedAt(TimeFormatter.format(post.getCreatedAt()))
                .build();
    }

    public static Post toPost(PostRequestDTO.createPost request, Member member, TreeHouse treeHouse){
        return Post.builder()
                .content(request.getContext())
                .writer(member)
                .treeHouse(treeHouse)
                .postImageList(new ArrayList<>())
                .build();
    }

    public static List<PostImage> toPostImageList(PostRequestDTO.createPost request){

        return request.getPictureUrlList().stream()
                .map(picture->
                            PostImage.builder()
                                    .imageUrl(picture)
                                    .build()).toList();
    }

    public static PostResponseDTO.createPostResult toCreatePostResult (Post post){
        return PostResponseDTO.createPostResult.builder()
                .postId(post.getId())
                .build();
    }
}
