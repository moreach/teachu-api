package ch.teachu.teachuapi.errorhandling;

public class FailedToFetchException extends RuntimeException {

  public FailedToFetchException(String message) {
    super("Failed to fetch: " + message);
  }
}
