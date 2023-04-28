package com.alpha.mongodb.entitymanager.exception;

import lombok.experimental.StandardException;

@StandardException
public class ManagedEntityRuntimeException extends RuntimeException {

    public ManagedEntityRuntimeException(String message) {
        super(message);
    }
}
