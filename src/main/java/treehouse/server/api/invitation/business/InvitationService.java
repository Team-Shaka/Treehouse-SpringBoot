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
import treehouse.server.global.entity.redis.RefreshToken;
import treehouse.server.global.entity.treeHouse.TreeHouse;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.ThrowClass.AuthException;
import treehouse.server.global.exception.ThrowClass.GeneralException;
import treehouse.server.global.redis.service.RedisService;
import treehouse.server.global.security.jwt.dto.TokenDTO;
import treehouse.server.global.security.provider.TokenProvider;

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
}
