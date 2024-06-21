package treehouse.server.api.invitation.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import treehouse.server.api.invitation.persistence.InvitationRepository;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.Invitation.Invitation;
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
public class InvitationCommandAdapter {

    private final InvitationRepository invitationRepository;


    public Invitation saveInvitation(Invitation invitation){
        return invitationRepository.save(invitation);
    }

}
