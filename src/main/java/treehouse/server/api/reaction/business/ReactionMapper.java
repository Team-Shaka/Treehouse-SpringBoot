package treehouse.server.api.reaction.business;

import treehouse.server.api.comment.presentation.dto.CommentRequestDTO;
import treehouse.server.api.post.presentation.dto.PostRequestDTO;
import treehouse.server.api.reaction.presentation.dto.ReactionResponseDTO;
import treehouse.server.global.entity.comment.Comment;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.post.Post;
import treehouse.server.global.entity.reaction.Reaction;
import treehouse.server.global.entity.reaction.TargetType;

import java.util.List;

public class ReactionMapper {

    public static Reaction toPostReaction(PostRequestDTO.reactToPost request, Post post, Member member){
        return Reaction.builder()
                .reactionName(request.getReactionName())
                .targetId(post.getId())
                .targetType(TargetType.POST)
                .member(member)
                .build();
    }

    public static Reaction toCommentReaction(CommentRequestDTO.reactToComment request, Comment comment, Member member) {
        return Reaction.builder()
                .reactionName(request.getReactionName())
                .targetId(comment.getId())
                .targetType(TargetType.COMMENT)
                .member(member)
                .build();
    }

    public static ReactionResponseDTO.getReaction toGetReaction(Reaction reaction, Integer reactionCount, Boolean isPushed) {
        return ReactionResponseDTO.getReaction.builder()
                .reactionName(reaction.getReactionName())
                .reactionCount(reactionCount)
                .isPushed(isPushed)
                .build();
    }

}
