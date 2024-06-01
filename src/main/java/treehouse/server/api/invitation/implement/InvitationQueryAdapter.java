package treehouse.server.api.invitation.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.invitation.persistence.InvitationRepository;
import treehouse.server.api.invitation.presentation.dto.InvitationRequestDTO;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.Invitation.Invitation;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.exception.GlobalErrorCode;
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
}
