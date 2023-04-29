package com.alpha.mongodb.entitymanager.exception;

public class EntityNotFoundException extends ManagedEntityRuntimeException {

    public EntityNotFoundException() {
        super("Entity not found. Cannot perform operation");
    }
}
