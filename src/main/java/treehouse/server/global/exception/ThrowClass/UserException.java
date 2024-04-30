package treehouse.server.global.exception.ThrowClass;

import treehouse.server.global.exception.BaseErrorCode;

public class UserException extends GeneralException{

    public UserException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
