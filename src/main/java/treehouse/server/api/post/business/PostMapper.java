package treehouse.server.api.post.business;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import treehouse.server.api.post.presentation.dto.PostRequestDTO;
import treehouse.server.api.post.presentation.dto.PostResponseDTO;
import treehouse.server.api.member.business.MemberMapper;
import treehouse.server.api.reaction.presentation.dto.ReactionResponseDTO;
import treehouse.server.global.common.util.TimeFormatter;
import treehouse.server.global.entity.branch.Branch;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.post.Post;
import treehouse.server.global.entity.post.PostImage;
import treehouse.server.global.entity.treeHouse.TreeHouse;
import treehouse.server.global.feign.dto.PresignedUrlDTO;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostMapper {

    public static PostResponseDTO.getPostDetails toGetPostDetails(Member member, List<Branch> branches, Post post, List<String> postImageUrlList, ReactionResponseDTO.getReactionList reactionList) {
        return PostResponseDTO.getPostDetails.builder()
                .memberProfile(MemberMapper.toGetWriterProfile(member, post.getWriter(), branches))
                .postId(post.getId())
                .context(post.getContent())
                .pictureUrlList(postImageUrlList)
                .commentCount(post.getCommentList().size())
                .reactionList(reactionList)
                .postedAt(TimeFormatter.format(post.getCreatedAt()))
                .build();
    }

    public static PostResponseDTO.getOnlyPostDetail toGetOnlyPostDetails(Post post, List<String> postImageUrlList, ReactionResponseDTO.getReactionList reactionList) {
        return PostResponseDTO.getOnlyPostDetail.builder()
                .postId(post.getId())
                .context(post.getContent())
                .pictureUrlList(postImageUrlList)
                .commentCount(post.getCommentList().size())
                .reactionList(reactionList)
                .postedAt(TimeFormatter.format(post.getCreatedAt()))
                .build();
    }

    public static PostResponseDTO.getMemberPostList toGetMemberPostList(Member member, Member targetMember, List<PostResponseDTO.getOnlyPostDetail> onlyPostDetailList, List<Branch> branches) {
        return PostResponseDTO.getMemberPostList.builder()
                .memberProfile(MemberMapper.toGetWriterProfile(member, targetMember, branches))
                .postList(onlyPostDetailList)
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

    public static PostResponseDTO.createPresignedUrlResult toCreatePresignedUrlResult(PresignedUrlDTO.PresignedUrlResult result){
        return PostResponseDTO.createPresignedUrlResult.builder()
                .uploadUrl(result.getUploadUrl())
                .accessUrl(result.getDownloadUrl())
                .build();
    }

    public static PostResponseDTO.updatePostResult toUpdatePostResult(Post post) {
        return PostResponseDTO.updatePostResult.builder()
                .postId(post.getId())
                .build();
    }
}
