package foi.air.szokpt.transproc.exceptions;

public class ExternalServiceException extends RuntimeException {
    public ExternalServiceException(String message) {
        super("An error occurred while communicating with an external service: " + message);
    }
}
