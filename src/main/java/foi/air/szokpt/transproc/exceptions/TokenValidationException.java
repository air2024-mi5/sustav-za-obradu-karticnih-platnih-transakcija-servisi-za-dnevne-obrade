package foi.air.szokpt.transproc.exceptions;

public class TokenValidationException extends RuntimeException {
    public TokenValidationException() {
        super("Token is invalid or expired");
    }
}
