package treehouse.server.api.invitation.business;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import treehouse.server.api.invitation.presentation.dto.InvitationResponseDTO;
import treehouse.server.global.entity.Invitation.Invitation;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.User.UserRole;
import treehouse.server.global.entity.User.UserStatus;

import java.util.List;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InvitationMapper {

    public InvitationResponseDTO.getInvitation toGetInvitation (Invitation invitation, List<String> treeMemberProfileImages) {
        return InvitationResponseDTO.getInvitation.builder()
                .invitationId(invitation.getId())
                .treehouseName(invitation.getTreeHouse().getName())
                .senderName(invitation.getSender().getName())
                .senderProfileImageUrl(invitation.getSender().getProfileImageUrl())
                .treehouseSize(invitation.getTreeHouse().getMemberList().size())
                .treehouseMemberProfileImages(treeMemberProfileImages)
                .build();
    }
    public InvitationResponseDTO.getInvitations toGetInvitations(List<InvitationResponseDTO.getInvitation> invitationDtos) {
        return InvitationResponseDTO.getInvitations.builder()
                .invitations(invitationDtos)
                .build();
    }

    public InvitationResponseDTO.myInvitationInfo toMyInvitationInfo(User user){
        return InvitationResponseDTO.myInvitationInfo.builder()
                .availableInvitation(user.getInvitationCount())
                .activeRate(user.getActiveRate())
                .build();
    }
}

