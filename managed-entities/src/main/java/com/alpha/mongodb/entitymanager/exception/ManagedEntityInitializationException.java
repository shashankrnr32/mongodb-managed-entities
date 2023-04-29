package com.alpha.mongodb.entitymanager.exception;

/**
 * Thrown when an entity is not able to be initialized
 */
public class ManagedEntityInitializationException extends ManagedEntityRuntimeException {

    public ManagedEntityInitializationException() {
        super("Entity cannot be attached");
    }
}
