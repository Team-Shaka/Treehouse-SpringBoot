package treehouse.server.api.reaction.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.reaction.Reaction;
import treehouse.server.global.entity.reaction.TargetType;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM Reaction r WHERE r.member = :member AND r.targetId = :targetId AND r.targetType = :targetType AND r.reactionName = :reactionName")
    Boolean existsByMemberAndTargetIdAndTargetTypeAndReactionName(@Param("member") Member member, @Param("targetId") Long targetId, @Param("targetType") TargetType targetType, @Param("reactionName") String reactionName);

    List<Reaction> findAllByMemberAndTargetIdAndTargetType(Member member, Long targetId, TargetType targetType);

    void deleteByMemberAndTargetIdAndTargetTypeAndReactionName(Member member, Long targetId, TargetType targetType, String reactionName);

    @Query("SELECT COUNT(r) FROM Reaction r WHERE r.targetId = :targetId AND r.targetType = :targetType AND r.reactionName = :reactionName")
    Integer countReactionsByReactionNameAndTargetIdAndTargetType(@Param("reactionName") String reactionName, @Param("targetId") Long targetId, @Param("targetType") TargetType targetType);

    List<Reaction> findAllByTargetIdAndTargetType(Long id, TargetType targetType);
}
