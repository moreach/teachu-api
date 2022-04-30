package ch.teachu.teachuapi.errorhandling;

public class NotFoundException extends RuntimeException {

  public NotFoundException(String message) {
    super("Not found: " + message);
  }
}
