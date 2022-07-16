package ch.teachu.teachuapi.parent.errorhandlig;

public class NotFoundException extends RuntimeException {

  public NotFoundException(String message) {
    super("Not found: " + message);
  }
}
