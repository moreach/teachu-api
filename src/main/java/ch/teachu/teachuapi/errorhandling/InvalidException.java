package ch.teachu.teachuapi.errorhandling;

public class InvalidException extends RuntimeException {

  public InvalidException(String message) {
    super("Invalid: " + message);
  }
}
