package treehouse.server.global.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import treehouse.server.global.feign.config.PresignedUrlLambdaConfiguration;
import treehouse.server.global.feign.dto.PresignedUrlDTO;

@FeignClient(
        name = "presignedUrlLambda",
        url = "${cloud.aws.lambda.url}",
        configuration = PresignedUrlLambdaConfiguration.class
)
public interface PresignedUrlLambdaClient {

    @GetMapping("/")
    PresignedUrlDTO.PresignedUrlResult getPresignedUrl(@RequestParam(name = "filename") String filename);
}


