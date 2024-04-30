package treehouse.server.api.user.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.user.implement.UserCommandAdapter;
import treehouse.server.api.user.implement.UserQueryAdapter;
import treehouse.server.api.user.presentation.dto.UserRequestDTO;
import treehouse.server.api.user.presentation.dto.UserResponseDTO;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.redis.RefreshToken;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.ThrowClass.AuthException;
import treehouse.server.global.exception.ThrowClass.GeneralException;
import treehouse.server.global.redis.service.RedisService;
import treehouse.server.global.security.jwt.dto.TokenDTO;
import treehouse.server.global.security.provider.TokenProvider;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final TokenProvider tokenProvider;

    private final UserQueryAdapter userQueryAdapter;

    private final UserCommandAdapter userCommandAdapter;

    private final RedisService redisService;

    @Transactional(readOnly = true)
    public UserResponseDTO.checkName checkName(UserRequestDTO.checkName request){
        return UserMapper.toCheckNameDTO(userQueryAdapter.checkName(request));
    }

    @Transactional
    public UserResponseDTO.registerUser register(UserRequestDTO.registerUser request){
        User user = UserMapper.toUser(request.getUserName(), request.getPhoneNumber());
        User savedUser = userCommandAdapter.register(user);
        TokenDTO loginResult = userCommandAdapter.login(savedUser);

        return UserMapper.toRegister(savedUser,loginResult.getAccessToken(), loginResult.getRefreshToken());
    }

    // temp
    @Transactional
    public UserResponseDTO.registerUser login(UserRequestDTO.loginMember request){
        User user = userQueryAdapter.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new GeneralException(GlobalErrorCode.MEMBER_NOT_FOUND));
        TokenDTO loginResult = userCommandAdapter.login(user);

        return UserMapper.toRegister(user,loginResult.getAccessToken(), loginResult.getRefreshToken());
    }

    @Transactional
    public UserResponseDTO.reissue reissue(UserRequestDTO.reissue request){
        RefreshToken refreshToken = redisService.findRefreshToken(request.getRefreshToken()).orElseThrow(() -> new AuthException(GlobalErrorCode.REFRESH_TOKEN_EXPIRED));
        User user = userQueryAdapter.findById(refreshToken.getUserId());
        TokenDTO token = userCommandAdapter.reissueToken(user,refreshToken);

        return UserMapper.toReissue(token.getAccessToken(), token.getRefreshToken());
    }


}
