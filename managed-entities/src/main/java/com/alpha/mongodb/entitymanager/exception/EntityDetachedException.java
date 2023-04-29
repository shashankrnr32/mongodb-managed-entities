package com.alpha.mongodb.entitymanager.exception;

/**
 * Thrown when an entity is detached and a proxied implementation is being called.
 */
public class EntityDetachedException extends ManagedEntityRuntimeException {

    public EntityDetachedException() {
        super("Entity Detached. Cannot perform operation");
    }
}
