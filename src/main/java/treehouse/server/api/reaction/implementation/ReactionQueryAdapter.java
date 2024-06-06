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
        //TODO: 현재 임시방편으로 모든 이모지 가져와 비교하는 형태. 추후 변경 필요
        List<Reaction> reactions = reactionRepository.findAllByMemberAndTargetIdAndTargetType(member, post.getId(), TargetType.POST);
        for (Reaction reaction : reactions) {

            if (reactionName.equals(reaction.getReactionName())) {
                return true;
            }
        }

        return false;
    }

    private boolean compareCodePoints(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }
        for (int i = 0; i < str1.length(); i++) {
            if (str1.codePointAt(i) != str2.codePointAt(i)) {
                return false;
            }
        }
        return true;
    }

    public Boolean existByMemberAndCommentAndReactionName(Member member, Comment comment, String reactionName) {
        //TODO: 현재 임시방편으로 모든 이모지 가져와 비교하는 형태. 추후 변경 필요
        List<Reaction> reactions = reactionRepository.findAllByMemberAndTargetIdAndTargetType(member, comment.getId(), TargetType.COMMENT);
        for (Reaction reaction : reactions) {

            if (reactionName.equals(reaction.getReactionName())) {
                return true;
            }
        }

        return false;
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
