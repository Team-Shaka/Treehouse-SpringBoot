package treehouse.server.api.member.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import treehouse.server.global.entity.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {


}
