package treehouse.server.global.exception;

import treehouse.server.global.exception.dto.ErrorResponseDTO;

public interface BaseErrorCode {

    public ErrorResponseDTO getReason();

    public ErrorResponseDTO getReasonHttpStatus();
}
