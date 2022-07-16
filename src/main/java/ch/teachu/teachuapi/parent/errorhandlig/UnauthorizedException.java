package ch.teachu.teachuapi.parent.errorhandlig;

public class UnauthorizedException extends RuntimeException {

  public UnauthorizedException(String message) {
    super("Unauthorized: " + message);
  }
}