package treehouse.server.global.security.handler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import treehouse.server.global.common.CommonResponse;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.JwtAuthenticationException;

import java.io.IOException;
import java.io.PrintWriter;

public class JwtAuthenticationExceptionHandler extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtAuthenticationException authException) {
            response.setContentType("application/json; charset=UTF-8");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            PrintWriter writer = response.getWriter();
            String errorCodeName = authException.getMessage();
            GlobalErrorCode errorCode = GlobalErrorCode.valueOf(errorCodeName);
            CommonResponse<String> apiErrorResult = CommonResponse.onFailure(errorCode.getCode(),errorCode.getMessage(), null);

            writer.write(apiErrorResult.toString());
            writer.flush();
            writer.close();
        }
    }
}
