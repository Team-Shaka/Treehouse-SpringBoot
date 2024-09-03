package treehouse.server.api.invitation.implement;

import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.invitation.persistence.InvitationRepository;
import treehouse.server.api.invitation.presentation.dto.InvitationRequestDTO;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.Invitation.Invitation;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.treeHouse.TreeHouse;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.ThrowClass.InvitationException;
import treehouse.server.global.exception.ThrowClass.UserException;

import java.util.List;
import java.util.Optional;

@Adapter
@RequiredArgsConstructor
public class InvitationQueryAdapter {

    private final InvitationRepository invitationRepository;

    public List<Invitation> findAllByPhone(String phone) {
        return invitationRepository.findAllByPhone(phone);
    }

    public Boolean existByPhoneNumber(String phoneNumber) {
        return invitationRepository.existsByPhone(phoneNumber);
    }

    public Invitation findById(Long invitationId) {
        return invitationRepository.findById(invitationId)
                .orElseThrow(() -> new InvitationException(GlobalErrorCode.INVITATION_NOT_FOUND));
    }

    public Boolean existByPhoneAndTreehouse(String phoneNumber, TreeHouse treehouse) {
        return invitationRepository.existsByPhoneAndTreeHouse(phoneNumber, treehouse);
    }

    public Invitation findByReceiverAndTreeHouse(User user, TreeHouse treeHouse) {
        return invitationRepository.findByReceiverAndTreeHouse(user, treeHouse)
                .orElseThrow(() -> new InvitationException(GlobalErrorCode.INVITATION_NOT_FOUND));
    }
}
