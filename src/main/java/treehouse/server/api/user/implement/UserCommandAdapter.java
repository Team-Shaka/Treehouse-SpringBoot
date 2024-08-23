package treehouse.server.api.user.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import treehouse.server.api.user.persistence.UserRepository;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.User.UserRole;
import treehouse.server.global.entity.redis.RefreshToken;
import treehouse.server.global.redis.service.RedisService;
import treehouse.server.global.security.jwt.dto.TokenDTO;
import treehouse.server.global.security.provider.TokenProvider;

import java.util.List;

@Adapter
@Slf4j
@RequiredArgsConstructor
public class UserCommandAdapter {

    private final UserRepository userRepository;

    private final RedisService redisService;

    private final TokenProvider tokenProvider;

    public User register(User user){ return userRepository.save(user);}

    public TokenDTO login(User user){
        String accessToken = tokenProvider.createAccessToken(user, List.of(new SimpleGrantedAuthority(UserRole.ROLE_USER.name())));
        String refreshToken = redisService.generateRefreshToken(user).getRefreshToken();

        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenDTO reissueToken(User user, RefreshToken refreshToken){

        String accessToken = tokenProvider.createAccessToken(user, List.of(new SimpleGrantedAuthority(UserRole.ROLE_USER.name())));
        RefreshToken newRefreshToken = redisService.reGenerateRefreshToken(user, refreshToken);

        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken.getRefreshToken())
                .build();
    }

    public void withdraw(User user) {
        userRepository.delete(user);
    }
}
