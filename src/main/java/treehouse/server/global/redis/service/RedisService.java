package treehouse.server.global.redis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.user.implement.UserQueryAdapter;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.redis.RefreshToken;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.ThrowClass.GeneralException;
import treehouse.server.global.redis.repository.RefreshTokenRepository;
import treehouse.server.global.security.provider.TokenProvider;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RedisService {

    private final UserQueryAdapter userQueryAdapter;

    private final RefreshTokenRepository refreshTokenRepository;

    private final TokenProvider tokenProvider;

    /**
     * 리프레시 토큰 발급
     * @param user
     * @return
     */
    public RefreshToken generateRefreshToken(User user) {
        if (!userQueryAdapter.existById(user.getId()))
            throw new GeneralException(GlobalErrorCode.USER_NOT_FOUND);

        String refreshToken = tokenProvider.createRefreshToken();

        return refreshTokenRepository.save(
                RefreshToken.builder()
                        .userId(user.getId())
                        .refreshToken(refreshToken)
                        .build());
    }


    public RefreshToken reGenerateRefreshToken(User user, RefreshToken refreshToken) {

        tokenProvider.validateRefreshToken(refreshToken.getRefreshToken());
        refreshTokenRepository.delete(refreshToken);

        String newRefreshToken = tokenProvider.createRefreshToken();

        return refreshTokenRepository.save(
                RefreshToken.builder()
                        .userId(user.getId())
                        .refreshToken(newRefreshToken)
                        .build());
    }


    public Optional<RefreshToken> findRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }
}
