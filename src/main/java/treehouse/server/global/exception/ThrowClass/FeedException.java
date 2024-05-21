package treehouse.server.global.exception.ThrowClass;

import treehouse.server.global.exception.BaseErrorCode;

public class FeedException extends GeneralException{

    public FeedException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
