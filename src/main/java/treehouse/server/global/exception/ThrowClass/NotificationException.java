package treehouse.server.global.exception.ThrowClass;

import treehouse.server.global.exception.BaseErrorCode;

public class NotificationException extends GeneralException{
    public NotificationException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
