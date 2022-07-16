package ch.teachu.teachuapi.shared.errorhandlig;

public class InvalidException extends RuntimeException {

  public InvalidException(String message) {
    super("Invalid: " + message);
  }
}
