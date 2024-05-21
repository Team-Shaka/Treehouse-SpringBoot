package treehouse.server.global.exception.ThrowClass;

import treehouse.server.global.exception.BaseErrorCode;

public class PostException extends GeneralException{

    public PostException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
