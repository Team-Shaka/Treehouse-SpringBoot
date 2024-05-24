package treehouse.server.global.exception.ThrowClass;

import treehouse.server.global.exception.BaseErrorCode;

public class CustomFeignException extends GeneralException{

    public CustomFeignException(BaseErrorCode errorCode){
        super(errorCode);
    }
}
