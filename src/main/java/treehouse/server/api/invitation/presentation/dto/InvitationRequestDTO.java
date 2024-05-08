package treehouse.server.api.invitation.presentation.dto;

import lombok.*;


public class InvitationRequestDTO {

    @Getter
    @NoArgsConstructor
    public static class invitationAcceptDecision{
        private Long invitationId;
        private boolean acceptDecision;

    }


}
