package treehouse.server.global.feign.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.ThrowClass.CustomFeignException;

public class FeignExceptionErrorDecoder implements ErrorDecoder {

    Logger logger = LoggerFactory.getLogger(FeignExceptionErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.status() >= 400 && response.status() <= 499) {
            logger.error("{}번 에러 발생 : {}", response.status(), response.reason());
            return new CustomFeignException(GlobalErrorCode.FEIGN_CLIENT_ERROR_400);
        } else {
            logger.error("500번대 에러 발생 : {}", response.reason());
            return new CustomFeignException(GlobalErrorCode.FEIGN_CLIENT_ERROR_500);
        }
    }
}
