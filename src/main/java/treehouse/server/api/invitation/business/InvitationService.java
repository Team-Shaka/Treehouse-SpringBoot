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
import treehouse.server.api.notification.business.NotificationService;
import treehouse.server.api.notification.presentation.dto.NotificationRequestDTO;
import treehouse.server.api.treehouse.implementation.TreehouseQueryAdapter;
import treehouse.server.api.user.implement.UserQueryAdapter;
import treehouse.server.global.entity.Invitation.Invitation;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.notification.NotificationType;
import treehouse.server.global.entity.treeHouse.TreeHouse;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.ThrowClass.InvitationException;
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

    private final NotificationService notificationService;

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
        // 남아있는 초대장이 있는지 확인
        if (user.getInvitationCount() <= 0) {
            throw new InvitationException(GlobalErrorCode.INVITATION_COUNT_ZERO);
        }

        // 트리 찾기
        TreeHouse treehouse = treehouseQueryAdapter.getTreehouseById(request.getTreehouseId());
        // 초대 멤버 찾기
        Member sender = memberQueryAdapter.findByUserAndTreehouse(user, treehouse);
        // 초대 대상이 가입된 사람인지 찾기
        User receiverUser = userQueryAdapter.findByPhoneNumberOptional(request.getPhoneNumber()).orElse(null);

        //동일한 초대장이 이미 존재하는지 확인하기
        if (invitationQueryAdapter.existByPhoneAndTreehouse(request.getPhoneNumber(), treehouse)) {
            throw new InvitationException(GlobalErrorCode.INVITATION_ALREADY_EXIST);
        }

        // 초대장 만들어서 저장하기

        Invitation invitation = invitationCommandAdapter.saveInvitation(InvitationMapper.toInvitation(request.getPhoneNumber(), sender, receiverUser, treehouse));

        // 초대자의 잔여 초대장 개수 차감
        user.reduceInvitationCount();

        //알림 생성
        if (receiverUser != null) {
            NotificationRequestDTO.createNotification notificationRequest = new NotificationRequestDTO.createNotification();
            notificationRequest.setReceiverId(receiverUser.getId()); // 여기서 receiver 설정 (예시)
            notificationRequest.setTargetId(invitation.getId());
            notificationRequest.setType(NotificationType.INVITATION); // 알림 타입 설정 (예시)
            notificationService.createNotification(user, invitation.getTreeHouse().getId(), notificationRequest, null);
        }
        // 리턴하기

        return InvitationMapper.toCreateInvitationDTO(invitation);
    }

    @Transactional
    public InvitationResponseDTO.invitationAccept decisionInvitation(User user, InvitationRequestDTO.invitationAcceptDecision request){
        // 해당 User 에게 온 초대장인지 검증하는 로직 추가
        Long treehouseId = 0L;
        Invitation invitation = invitationQueryAdapter.findById(request.getInvitationId());

        if (request.isAcceptDecision()==true) {
            treehouseId = invitation.getTreeHouse().getId(); // treehouse 관련 로직 개발 후, invitation.getTreeHouse.getId() 등으로 바꾸기
        }
        return InvitationMapper.toInvitationResult(treehouseId);
    }
}
