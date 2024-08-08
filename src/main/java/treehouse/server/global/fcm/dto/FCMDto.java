package treehouse.server.global.fcm.dto;

import lombok.*;

public class FCMDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FCMTestDto{
        String fcmToken;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class saveFcmTokenDto{
        String fcmToken;
    }
}
