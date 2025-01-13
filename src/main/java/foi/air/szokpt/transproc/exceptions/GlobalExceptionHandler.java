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
}
