package ch.teachu.teachuapi.errorhandling;

public class UnauthorizedException extends RuntimeException {

  public UnauthorizedException(String message) {
    super("Unauthorized: " + message);
  }
}