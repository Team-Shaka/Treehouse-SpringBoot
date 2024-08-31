package treehouse.server.api.invitation.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import treehouse.server.global.entity.Invitation.Invitation;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.treeHouse.TreeHouse;

import java.util.List;
import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    List<Invitation> findAllByPhone(String phone);

    Invitation findByPhoneAndTreeHouse(String phone, TreeHouse treeHouse);

    Boolean existsByPhone(String phoneNumber);

    Boolean existsByPhoneAndTreeHouse(String phoneNumber, TreeHouse treehouse);
}
