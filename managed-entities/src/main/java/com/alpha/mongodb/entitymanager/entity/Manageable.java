package com.alpha.mongodb.entitymanager.entity;

/**
 * A manageable entity that can be detached from the session
 * thereafter, cannot be managed through the entity.
 *
 * @author Shashank Sharma
 */
public interface Manageable {

    /**
     * Check if the current entity is in a managed session
     *
     * @return true if managed, false otherwise
     */
    default boolean isManaged() {
        return false;
    }

    /**
     * Detach an entity from the managed session thereby
     * making all the proxied implementations obsolete
     */
    default void detach() {
    }
}
