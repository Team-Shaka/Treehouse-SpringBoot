package treehouse.server.api.member.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.member.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public Optional<Member> findByUser(User user);
}
