package treehouse.server.api.reaction.implementation;

import lombok.RequiredArgsConstructor;
import treehouse.server.api.reaction.persistence.ReactionRepository;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.comment.Comment;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.post.Post;
import treehouse.server.global.entity.reaction.Reaction;
import treehouse.server.global.entity.reaction.TargetType;

import java.util.List;

@Adapter
@RequiredArgsConstructor
public class ReactionQueryAdapter {

    private final ReactionRepository reactionRepository;

    public Boolean existByMemberAndPostAndReactionName(Member member, Post post, String reactionName) {
        boolean isPushed = reactionRepository.existsByMemberAndTargetIdAndTargetTypeAndReactionName(member, post.getId(), TargetType.POST, reactionName);
        return isPushed;
    }

    public Boolean existByMemberAndCommentAndReactionName(Member member, Comment comment, String reactionName) {
        boolean isPushed = reactionRepository.existsByMemberAndTargetIdAndTargetTypeAndReactionName(member, comment.getId(), TargetType.COMMENT, reactionName);
        return isPushed;
    }

    public List<Reaction> findAllByPost(Post post) {
        return reactionRepository.findAllByTargetIdAndTargetType(post.getId(), TargetType.POST);
    }

    public List<Reaction> findAllByComment(Comment comment) {
        return reactionRepository.findAllByTargetIdAndTargetType(comment.getId(), TargetType.COMMENT);
    }

    public Integer countReactionsByReactionNameAndPostId(String reactionName, Long postId) {
        return reactionRepository.countReactionsByReactionNameAndTargetIdAndTargetType(reactionName, postId, TargetType.POST);
    }

    public Integer countReactionsByReactionNameAndCommentId(String reactionName, Long commentId) {
        return reactionRepository.countReactionsByReactionNameAndTargetIdAndTargetType(reactionName, commentId, TargetType.COMMENT);
    }
}
