package treehouse.server.api.user.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.invitation.implement.InvitationCommandAdapter;
import treehouse.server.api.invitation.implement.InvitationQueryAdapter;
import treehouse.server.api.user.implement.UserCommandAdapter;
import treehouse.server.api.user.implement.UserQueryAdapter;
import treehouse.server.api.user.persistence.UserRepository;
import treehouse.server.api.user.presentation.dto.UserRequestDTO;
import treehouse.server.api.user.presentation.dto.UserResponseDTO;
import treehouse.server.global.entity.Invitation.Invitation;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.redis.RefreshToken;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.ThrowClass.AuthException;
import treehouse.server.global.exception.ThrowClass.GeneralException;
import treehouse.server.global.fcm.service.FcmService;
import treehouse.server.global.redis.service.RedisService;
import treehouse.server.global.security.jwt.dto.TokenDTO;
import treehouse.server.global.security.provider.TokenProvider;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final TokenProvider tokenProvider;

    private final UserQueryAdapter userQueryAdapter;

    private final UserCommandAdapter userCommandAdapter;

    private final RedisService redisService;

    private final InvitationCommandAdapter invitationCommandAdapter;
    private final InvitationQueryAdapter invitationQueryAdapter;
    private final UserRepository userRepository;

    private final FcmService fcmService;

    @Transactional(readOnly = true)
    public UserResponseDTO.checkName checkName(UserRequestDTO.checkName request){
        return UserMapper.toCheckNameDTO(userQueryAdapter.checkName(request));
    }

    @Transactional(readOnly = true)
    public User findById(Long id){
        return userQueryAdapter.findById(id);
    }

    @Transactional
    public UserResponseDTO.registerUser register(UserRequestDTO.registerUser request){
        User user = UserMapper.toUser(request.getUserName(), request.getPhoneNumber());
        User savedUser = userCommandAdapter.register(user);
        List<Invitation> receivedInvitations = invitationQueryAdapter.findAllByPhone(request.getPhoneNumber());
        receivedInvitations.forEach(invitation -> {
            invitation.setReceiver(savedUser);
            invitationCommandAdapter.saveInvitation(invitation);
        });
        TokenDTO loginResult = userCommandAdapter.login(savedUser);

        return UserMapper.toRegister(savedUser,loginResult.getAccessToken(), loginResult.getRefreshToken());
    }

    // temp
    @Transactional
    public UserResponseDTO.loginMember login(UserRequestDTO.loginMember request){
        User user = userQueryAdapter.findByPhoneNumber(request.getPhoneNumber());

        TokenDTO loginResult = userCommandAdapter.login(user);

        List<Member> memberList = user.getMemberList();
        List<Long> treehouseIdList = memberList.stream()
                .map(member -> member.getTreeHouse().getId())
                .collect(Collectors.toList());  // 특정 유저가 가입한 모든 트리하우스들의 id를 구하는 과정

        return UserMapper.toLogin(user,loginResult.getAccessToken(), loginResult.getRefreshToken(), treehouseIdList);
    }

    @Transactional
    public UserResponseDTO.reissue reissue(UserRequestDTO.reissue request){
        RefreshToken refreshToken = redisService.findRefreshToken(request.getRefreshToken()).orElseThrow(() -> new AuthException(GlobalErrorCode.REFRESH_TOKEN_EXPIRED));
        User user = userQueryAdapter.findById(refreshToken.getUserId());
        TokenDTO token = userCommandAdapter.reissueToken(user,refreshToken);

        return UserMapper.toReissue(token.getAccessToken(), token.getRefreshToken());
    }


    public UserResponseDTO.checkUserStatus checkUserStatus(UserRequestDTO.checkUserStatus request) {

        Boolean isNewUser = !userQueryAdapter.existByPhoneNumber(request.getPhoneNumber());
        Boolean isInvited = invitationQueryAdapter.existByPhoneNumber(request.getPhoneNumber());

        return UserMapper.toCheckUserStatus(isNewUser, isInvited);
    }

    @Transactional
    public UserResponseDTO.withdraw withdraw(User user) {
        userCommandAdapter.withdraw(user);

        return UserMapper.toWithdraw(user);
    }

    @Transactional
    public UserResponseDTO.pushAgree updatePushAgree(User user, UserRequestDTO.pushAgreeDto request){
        boolean pushAgree = user.updatePushAgree(request.isPushAgree());
        return UserMapper.toPushAgree(user, request);
    }
}
