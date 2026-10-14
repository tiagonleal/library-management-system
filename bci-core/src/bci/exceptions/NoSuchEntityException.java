package bci.exceptions;

import java.io.Serial;

/**
 * Exception thrown when an entity (user, work, creator) is not found in the library.
 * This is the core exception that is converted to specific exceptions in the app layer.
 */
public class NoSuchEntityException extends Exception {

    @Serial
    private static final long serialVersionUID = 202507171003L;

    private final String _entityType;
    private final String _entityId;

    /**
     * Creates a new NoSuchEntityException.
     * 
     * @param entityType the type of entity that was not found (e.g., "utente", "obra", "criador")
     * @param entityId the ID of the entity that was not found
     */
    public NoSuchEntityException(String entityType, String entityId) {
        super("Não existe " + entityType + " com o id " + entityId);
        _entityType = entityType;
        _entityId = entityId;
    }

    /**
     * Gets the type of entity that was not found.
     * 
     * @return the entity type
     */
    public String getEntityType() {
        return _entityType;
    }

    /**
     * Gets the ID of the entity that was not found.
     * 
     * @return the entity ID
     */
    public String getEntityId() {
        return _entityId;
    }
}