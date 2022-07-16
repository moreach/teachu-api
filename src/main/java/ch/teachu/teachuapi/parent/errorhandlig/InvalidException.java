package ch.teachu.teachuapi.parent.errorhandlig;

public class InvalidException extends RuntimeException {

  public InvalidException(String message) {
    super("Invalid: " + message);
  }
}
