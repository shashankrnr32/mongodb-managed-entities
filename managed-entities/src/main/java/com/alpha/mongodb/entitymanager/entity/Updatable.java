package com.alpha.mongodb.entitymanager.entity;

import com.alpha.mongodb.entitymanager.exception.ManagedEntityRuntimeException;

/**
 * A managed entity that can be updated.
 *
 * @author Shashank Sharma
 */
public interface Updatable extends Manageable {

    /**
     * Updates the entity and refreshes it.
     *
     * @throws ManagedEntityRuntimeException Managed Entity Runtime exception
     */
    default void update() throws ManagedEntityRuntimeException {
    }
}
