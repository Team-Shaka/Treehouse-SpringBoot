package treehouse.server.api.invitation.implement;

import lombok.RequiredArgsConstructor;
import treehouse.server.api.invitation.persistence.InvitationRepository;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.Invitation.Invitation;
import treehouse.server.global.entity.Invitation.InvitationStatus;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.treeHouse.TreeHouse;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.ThrowClass.InvitationException;

import java.util.List;

@Adapter
@RequiredArgsConstructor
public class InvitationQueryAdapter {

    private final InvitationRepository invitationRepository;

    public List<Invitation> findAllPendingByPhone(String phone) {
        return invitationRepository.findAllByPhone(phone)
                .stream().filter(invitation -> invitation.getStatus().equals(InvitationStatus.PENDING)).toList();
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

    public Invitation findByPhoneAndTreeHouse(String phone, TreeHouse treeHouse) {
        return invitationRepository.findByPhoneAndTreeHouse(phone, treeHouse);
    }

    public Invitation findAcceptedInvitation(User user, TreeHouse treeHouse) {
        return invitationRepository.findByReceiverAndTreeHouse(user, treeHouse)
                .filter(invitation -> invitation.getStatus().equals(InvitationStatus.ACCEPTED))
                .orElseThrow(() -> new InvitationException(GlobalErrorCode.INVITATION_NOT_FOUND));
    }

    public List<Invitation> findAllByPhone(String phone) {
        return invitationRepository.findAllByPhone(phone);
    }
}
