package ch.teachu.teachuapi.shared.errorhandlig;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super("Unauthorized: " + message);
    }
}