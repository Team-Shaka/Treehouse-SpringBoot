package treehouse.server.api.invitation.presentation.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InvitationRequestDTO {

    @Getter
    @NoArgsConstructor
    public static class invitationAcceptDecision{
        private Long invitationId;
        private boolean acceptDecision;

    }

    @Getter
    @NoArgsConstructor
    public static class createInvitation{
        private Long senderId;
        private String phoneNumber;
        private Long treehouseId;
    }


}
