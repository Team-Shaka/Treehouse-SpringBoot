package treehouse.server.api.invitation.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.invitation.implement.InvitationCommandAdapter;
import treehouse.server.api.invitation.implement.InvitationQueryAdapter;
import treehouse.server.api.invitation.presentation.dto.InvitationRequestDTO;
import treehouse.server.api.invitation.presentation.dto.InvitationResponseDTO;
import treehouse.server.global.entity.Invitation.Invitation;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.treeHouse.TreeHouse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class InvitationService {


    private final InvitationQueryAdapter invitationQueryAdapter;

    private final InvitationCommandAdapter invitationCommandAdapter;
    private final InvitationMapper invitationMapper;
    private static final Integer treeMemberRandomProfileSize = 3;


    @Transactional
    public InvitationResponseDTO.getInvitations getInvitations(User user) {

        List<Invitation> invitations = invitationQueryAdapter.findAllByPhone(user.getPhone());

        List<InvitationResponseDTO.getInvitation> invitationDtos = invitations.stream()
                .map(invitation -> {
                    TreeHouse treeHouse = invitation.getTreeHouse();
                    List<Member> treeMembers = treeHouse.getMemberList();
                    List<String> randomProfileImages = treeMembers.stream()
                            .map(Member::getProfileImageUrl)
                            .limit(treeMemberRandomProfileSize)
                            .toList();
                    return invitationMapper.toGetInvitation(invitation, randomProfileImages);
                })
                .collect(Collectors.toList());
        return invitationMapper.toGetInvitations(invitationDtos);
    }

    public InvitationResponseDTO.myInvitationInfo getMyInvitationInfo(User user){
        return invitationMapper.toMyInvitationInfo(user);
    }

    @Transactional
    public InvitationResponseDTO.invitationAccept decisionInvitation(User user, InvitationRequestDTO.invitationAcceptDecision request){
        // 해당 User 에게 온 초대장인지 검증하는 로직 추가
        Long treehouseId = 0L;
        if (request.isAcceptDecision()==true) {
            treehouseId = 1L; // treehouse 관련 로직 개발 후, invitation.getTreeHouse.getId() 등으로 바꾸기
        }
        return invitationMapper.toInvitationResult(treehouseId);
    }
}
