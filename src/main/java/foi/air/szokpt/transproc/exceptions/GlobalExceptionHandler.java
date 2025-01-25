package foi.air.szokpt.transproc.exceptions;

import foi.air.szokpt.transproc.util.ApiResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TransactionProcessingException.class)
    public ResponseEntity<Object> handleTransactionProcessingException(
            TransactionProcessingException ex) {
        return new ResponseEntity<>(ApiResponseUtil.failure(ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<Object> handleValidationException(ExternalServiceException ex) {
        return new ResponseEntity<>(ApiResponseUtil.failure("Error fetching transactions"),
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(TokenValidationException.class)
    public ResponseEntity<Object> handleValidationException(TokenValidationException ex) {
        return new ResponseEntity<>(ApiResponseUtil.failure("Invalid or expired token."),
                HttpStatus.UNAUTHORIZED);
    }
}
