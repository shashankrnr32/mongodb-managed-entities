package com.alpha.mongodb.entitymanager.exception;

import lombok.experimental.StandardException;

/**
 * Managed Entity Runtime exception
 */
@StandardException
public class ManagedEntityRuntimeException extends RuntimeException {

    public ManagedEntityRuntimeException(String message) {
        super(message);
    }
}
