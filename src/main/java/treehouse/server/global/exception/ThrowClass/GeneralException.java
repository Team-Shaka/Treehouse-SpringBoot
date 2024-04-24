package treehouse.server.global.exception.ThrowClass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import treehouse.server.global.exception.BaseErrorCode;
import treehouse.server.global.exception.dto.ErrorResponseDTO;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private final BaseErrorCode errorCode;

    public ErrorResponseDTO getErrorReason() {
        return this.errorCode.getReason();
    }

    public ErrorResponseDTO getErrorReasonHttpStatus() {
        return this.errorCode.getReasonHttpStatus();
    }
}
