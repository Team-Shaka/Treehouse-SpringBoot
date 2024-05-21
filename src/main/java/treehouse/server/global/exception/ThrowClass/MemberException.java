package treehouse.server.global.exception.ThrowClass;

import treehouse.server.global.exception.BaseErrorCode;

public class MemberException extends GeneralException{

    public MemberException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
