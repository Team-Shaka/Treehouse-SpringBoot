package treehouse.server.global.exception.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorResponseDTO {

    private HttpStatus httpStatus;

    private final boolean isSuccess;
    private final String code;
    private final String message;
}
