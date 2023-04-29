package com.alpha.mongodb.entitymanager.entity;

import com.alpha.mongodb.entitymanager.exception.ManagedEntityRuntimeException;

/**
 * An enhanced entity that can be deleted.
 *
 * @author Shashank Sharma
 */
public interface Deletable extends Manageable {

    /**
     * Deletes the current entity and detaches it
     *
     * @throws ManagedEntityRuntimeException Managed Entity Runtime exception
     */
    default void delete() throws ManagedEntityRuntimeException {
    }
}
