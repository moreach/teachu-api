package ch.teachu.teachuapi.util;

import ch.teachu.teachuapi.errorhandling.InvalidException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Assert {

    public void ensureTrue(boolean expression, String message) {
        if (!expression) {
            throw new InvalidException(message);
        }
    }

    public void ensureFalse(boolean expression, String message) {
        if (expression) {
            throw new InvalidException(message);
        }
    }

    public static void ensureNotNull(Object obj, String message) {
        if (obj == null) {
            throw new InvalidException(message);
        }
    }

    public static void ensureNull(Object obj, String message) {
        if (obj != null) {
            throw new InvalidException(message);
        }
    }
}
