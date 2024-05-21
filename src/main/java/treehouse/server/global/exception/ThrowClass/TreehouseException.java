package treehouse.server.global.exception.ThrowClass;

import treehouse.server.global.exception.BaseErrorCode;

public class TreehouseException extends GeneralException{

    public TreehouseException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
