package com.alpha.mongodb.entitymanager.exception;

public class EntityDetachedException extends ManagedEntityRuntimeException {

    public EntityDetachedException() {
        super("Entity Detached. Cannot perform operation");
    }
}
