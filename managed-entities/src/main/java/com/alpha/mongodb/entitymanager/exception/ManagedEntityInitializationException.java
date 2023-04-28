package com.alpha.mongodb.entitymanager.exception;

public class ManagedEntityInitializationException extends ManagedEntityRuntimeException {

    public ManagedEntityInitializationException() {
        super("Entity cannot be attached");
    }
}
