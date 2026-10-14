package bci.exceptions;

import java.io.Serial;

/**
 * Exception thrown when a user with the given ID does not exist.
 */
public class NoSuchCreatorException extends Exception {

    @Serial
    private static final long serialVersionUID = 202507171003L;

    private final String _creatorId;

    public NoSuchCreatorException(String creatorId) {
        super("Não existe criador com o id " + creatorId);
        _creatorId = creatorId;
    }

    public String getCreatorId() {
        return _creatorId;
    }
}
