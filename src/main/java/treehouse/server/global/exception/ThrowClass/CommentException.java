package treehouse.server.global.exception.ThrowClass;

import treehouse.server.global.exception.BaseErrorCode;

public class CommentException extends GeneralException{

    public CommentException(BaseErrorCode errorCode){
        super(errorCode);
    }
}
