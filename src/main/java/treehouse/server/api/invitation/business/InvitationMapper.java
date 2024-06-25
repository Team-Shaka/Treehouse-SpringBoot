package treehouse.server.api.invitation.business;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import treehouse.server.api.invitation.presentation.dto.InvitationResponseDTO;
import treehouse.server.global.entity.Invitation.Invitation;
import treehouse.server.global.entity.Invitation.InvitationStatus;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.User.UserRole;
import treehouse.server.global.entity.User.UserStatus;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.treeHouse.TreeHouse;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InvitationMapper {

    public static InvitationResponseDTO.getInvitation toGetInvitation (Invitation invitation, List<String> treeMemberProfileImages) {
        return InvitationResponseDTO.getInvitation.builder()
                .invitationId(invitation.getId())
                .treehouseName(invitation.getTreeHouse().getName())
                .senderName(invitation.getSender().getName())
                .senderProfileImageUrl(invitation.getSender().getProfileImageUrl())
                .treehouseSize(invitation.getTreeHouse().getMemberList().size())
                .treehouseMemberProfileImages(treeMemberProfileImages)
                .build();
    }
    public static InvitationResponseDTO.getInvitations toGetInvitations(List<InvitationResponseDTO.getInvitation> invitationDtos) {
        return InvitationResponseDTO.getInvitations.builder()
                .invitations(invitationDtos)
                .build();
    }

    public static InvitationResponseDTO.myInvitationInfo toMyInvitationInfo(User user){
        return InvitationResponseDTO.myInvitationInfo.builder()
                .availableInvitation(user.getInvitationCount())
                .activeRate(user.getActiveRate())
                .build();
    }

    public static InvitationResponseDTO.invitationAccept toInvitationResult(Long treeHouseId){
        return InvitationResponseDTO.invitationAccept.builder()
                .treehouseId(treeHouseId)
                .build();
    }

    public static Invitation toInvitation(String phoneNumber, Member sender, User receiver, TreeHouse treeHouse){


        LocalDateTime now = LocalDateTime.now();

        // 7일 뒤 시간 계산하기
        LocalDateTime sevenDaysLater = now.plus(7, ChronoUnit.DAYS);

        return Invitation.builder()
                .sender(sender)
                .receiver(receiver)
                .phone(phoneNumber)
                .expiredAt(sevenDaysLater)
                .status(InvitationStatus.PENDING)
                .build();
    }

    public static InvitationResponseDTO.createInvitation toCreateInvitationDTO (Invitation invitation){
        return InvitationResponseDTO.createInvitation.builder()
                .invitationId(invitation.getId())
                .build();
    }
}

