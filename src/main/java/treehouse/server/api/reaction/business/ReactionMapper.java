package treehouse.server.api.reaction.business;

import treehouse.server.api.post.presentation.dto.PostRequestDTO;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.post.Post;
import treehouse.server.global.entity.reaction.Reaction;
import treehouse.server.global.entity.reaction.TargetType;

public class ReactionMapper {

    public static Reaction toPostReaction(PostRequestDTO.reactToPost request, Post post, Member member){
        return Reaction.builder()
                .reactionName(request.getReactionName())
                .targetId(post.getId())
                .targetType(TargetType.POST)
                .member(member)
                .build();
    }
}
