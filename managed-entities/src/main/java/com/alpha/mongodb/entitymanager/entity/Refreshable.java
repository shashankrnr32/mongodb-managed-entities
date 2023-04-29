package com.alpha.mongodb.entitymanager.entity;

import com.alpha.mongodb.entitymanager.exception.ManagedEntityRuntimeException;

/**
 * An entity that can be refreshed within a managed session.
 * An entity to be implementing this interface has to implement
 * {@link Refreshable#getId()} to be able to refresh.
 *
 * @author Shashank Sharma
 */
public interface Refreshable extends Manageable {

    /**
     * Refreshes this entity.
     *
     * @throws ManagedEntityRuntimeException Managed Entity Runtime exception
     */
    default void refresh() throws ManagedEntityRuntimeException {
    }

    String getId();
}
