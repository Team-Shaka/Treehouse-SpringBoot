package treehouse.server.global.feign.config;

import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import treehouse.server.global.feign.exception.FeignExceptionErrorDecoder;

@RequiredArgsConstructor
public class PresignedUrlLambdaConfiguration {


    @Bean
    public RequestInterceptor requestInterceptor(){
        return template -> template.header("Content-Type", "application/json;charset=UTF-8");
    }


    @Bean
    public ErrorDecoder errorDecoder() {return new FeignExceptionErrorDecoder();}

    @Bean
    Logger.Level feignLoggerLevel(){return Logger.Level.FULL;}
}
