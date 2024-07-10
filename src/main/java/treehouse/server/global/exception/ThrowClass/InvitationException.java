package treehouse.server.global.exception.ThrowClass;

import treehouse.server.global.exception.BaseErrorCode;

public class InvitationException extends GeneralException{

    public InvitationException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
