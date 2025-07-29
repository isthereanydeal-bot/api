package isthereanydeal.app.common.error;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.servlet.http.HttpServletRequest;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatus(ResponseStatusException ex, HttpServletRequest req) {
        ErrorResponse body = new ErrorResponse(
                Instant.now(),
                ex.getStatusCode().value(),
                resolveReasonPhrase(ex.getStatusCode()),
                ex.getReason(),
                req.getRequestURI());
        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }

    private String resolveReasonPhrase(HttpStatusCode statusCode) {
    if (statusCode instanceof HttpStatus httpStatus) {
        return httpStatus.getReasonPhrase();
    }
    try {
        return HttpStatus.valueOf(statusCode.value()).getReasonPhrase();
    } catch (IllegalArgumentException e) {
        return "Unknown";
    }
}

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAny(Exception ex, HttpServletRequest req) {
        ErrorResponse body = new ErrorResponse(Instant.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    public record ErrorResponse(Instant timestamp, int status, String error, String message, String path) {
    }
}
