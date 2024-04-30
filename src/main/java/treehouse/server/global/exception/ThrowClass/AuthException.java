package treehouse.server.global.exception.ThrowClass;

import treehouse.server.global.exception.BaseErrorCode;

public class AuthException extends GeneralException{

    public AuthException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
