package treehouse.server.global.feign.dto;

import lombok.*;

public class PresignedUrlDTO {

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PresignedUrlResult{
        String uploadUrl;
        String downloadUrl;
    }
}
