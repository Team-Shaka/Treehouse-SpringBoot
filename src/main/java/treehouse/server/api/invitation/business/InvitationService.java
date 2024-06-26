package treehouse.server.api.invitation.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.invitation.implement.InvitationCommandAdapter;
import treehouse.server.api.invitation.implement.InvitationQueryAdapter;
import treehouse.server.api.invitation.presentation.dto.InvitationRequestDTO;
import treehouse.server.api.invitation.presentation.dto.InvitationResponseDTO;
import treehouse.server.api.member.implementation.MemberQueryAdapter;
import treehouse.server.api.treehouse.implementation.TreehouseQueryAdapter;
import treehouse.server.api.user.implement.UserQueryAdapter;
import treehouse.server.global.entity.Invitation.Invitation;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.treeHouse.TreeHouse;
import treehouse.server.global.exception.ThrowClass.UserException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class InvitationService {


    private final InvitationQueryAdapter invitationQueryAdapter;

    private final InvitationCommandAdapter invitationCommandAdapter;

    private final TreehouseQueryAdapter treehouseQueryAdapter;

    private final MemberQueryAdapter memberQueryAdapter;

    private final UserQueryAdapter userQueryAdapter;

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
                    return InvitationMapper.toGetInvitation(invitation, randomProfileImages);
                })
                .collect(Collectors.toList());
        return InvitationMapper.toGetInvitations(invitationDtos);
    }

    public InvitationResponseDTO.myInvitationInfo getMyInvitationInfo(User user){
        return InvitationMapper.toMyInvitationInfo(user);
    }

    @Transactional
    public InvitationResponseDTO.createInvitation createInvitation(User user, InvitationRequestDTO.createInvitation request){

        // 트리 찾기
        TreeHouse treehouse = treehouseQueryAdapter.getTreehouseById(request.getTreehouseId());
        // 초대 멤버 찾기
        Member sender = memberQueryAdapter.findByUserAndTreehouse(user, treehouse);
        // 초대 대상이 가입된 사람인지 찾기

        User receiverUser = null;

        try {
            receiverUser = userQueryAdapter.findByPhoneNumber(request.getPhoneNumber());
        }
        catch (UserException e){
            // 뭐 안함
        }
        // 초대장 만들어서 저장하기

        Invitation invitation = invitationCommandAdapter.saveInvitation(InvitationMapper.toInvitation(request.getPhoneNumber(), sender, receiverUser, treehouse));

        // 리턴하기

        return InvitationMapper.toCreateInvitationDTO(invitation);
    }

    @Transactional
    public InvitationResponseDTO.invitationAccept decisionInvitation(User user, InvitationRequestDTO.invitationAcceptDecision request){
        // 해당 User 에게 온 초대장인지 검증하는 로직 추가
        Long treehouseId = 0L;
        if (request.isAcceptDecision()==true) {
            treehouseId = 1L; // treehouse 관련 로직 개발 후, invitation.getTreeHouse.getId() 등으로 바꾸기
        }
        return InvitationMapper.toInvitationResult(treehouseId);
    }
}
