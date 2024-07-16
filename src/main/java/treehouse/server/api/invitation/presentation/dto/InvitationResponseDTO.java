package treehouse.server.api.invitation.presentation.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InvitationResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getInvitation {
        private Long invitationId;
        private Long treehouseId;
        private String treehouseName;
        private String senderName;
        private String senderProfileImageUrl;
        private Integer treehouseSize;
        private List<String> treehouseMemberProfileImages;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getInvitations {
        List<InvitationResponseDTO.getInvitation> invitations;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class myInvitationInfo{
        private Integer availableInvitation;
        private Integer activeRate;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class invitationAccept {
        private Long treehouseId;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class createInvitation{
        private Long invitationId;
    }
}
