package treehouse.server.api.reaction.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import treehouse.server.api.reaction.persistence.ReactionRepository;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.comment.Comment;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.post.Post;
import treehouse.server.global.entity.reaction.Reaction;
import treehouse.server.global.entity.reaction.TargetType;

@Adapter
@RequiredArgsConstructor
@Slf4j
public class ReactionCommandAdapter {

    private final ReactionRepository reactionRepository;

    public Reaction saveReaction(Reaction reaction){
        return reactionRepository.save(reaction);
    }

    public void deletePostReaction(Member member, Post post, String reactionName) {
        reactionRepository.deleteByMemberAndTargetIdAndTargetTypeAndReactionName(member, post.getId(), TargetType.POST, reactionName);
    }

    public void deleteCommentReaction(Member member, Comment comment, String reactionName) {
        reactionRepository.deleteByMemberAndTargetIdAndTargetTypeAndReactionName(member, comment.getId(), TargetType.COMMENT, reactionName);
    }
}
