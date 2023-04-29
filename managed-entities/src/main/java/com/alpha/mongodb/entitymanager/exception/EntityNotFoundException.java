package com.alpha.mongodb.entitymanager.exception;

/**
 * Thrown when a refresh is called on an entity that doesn't exist in the database
 */
public class EntityNotFoundException extends ManagedEntityRuntimeException {

    public EntityNotFoundException() {
        super("Entity not found. Cannot perform operation");
    }
}
