package treehouse.server.global.exception.ThrowClass;

import treehouse.server.global.exception.BaseErrorCode;

public class FcmException extends GeneralException {
    public FcmException(BaseErrorCode errorCode) {
        super(errorCode);
    }

}
