package treehouse.server.global.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import treehouse.server.global.feign.config.FCMFeignConfig;
import treehouse.server.global.feign.dto.FCMResponseDto;

@FeignClient(name = "FCMFeign", url = "https://fcm.googleapis.com", configuration = FCMFeignConfig.class)
@Component
public interface FCMFeignClient {

    @PostMapping("/v1/projects/treehouse-95103/messages:send")
    FCMResponseDto getFCMResponse(@RequestHeader("Authorization") String token, @RequestBody String fcmAOSMessage);

}
