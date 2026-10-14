package bci.exceptions;

import java.io.Serial;

/**
 * Exception thrown when user registration fails.
 */
public class UserRegistrationFailedException extends Exception {

    @Serial
    private static final long serialVersionUID = 202507171003L;

    public UserRegistrationFailedException(String message) {
        super(message);
    }

    public UserRegistrationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
