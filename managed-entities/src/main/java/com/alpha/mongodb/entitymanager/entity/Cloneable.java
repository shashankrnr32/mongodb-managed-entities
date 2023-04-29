package com.alpha.mongodb.entitymanager.entity;

import com.alpha.mongodb.entitymanager.exception.ManagedEntityRuntimeException;
import org.apache.commons.lang3.NotImplementedException;

/**
 * An entity that can be cloned/copied
 *
 * @author Shashank Sharma
 */
public interface Cloneable extends Manageable {

    /**
     * Provides an implementation of cloning a document and saving it inside the database.
     * A callback can be provided that will be applied after copying the entity
     *
     * @param callback Callback to be applied on the copied entity before saving it to the database
     * @param <E>      type of the entity
     * @return a managed entity that is a clone of this object
     * @throws ManagedEntityRuntimeException Managed Entity Runtime exception
     */
    default <E extends Manageable> E clone(CopiedEntityCallback<E> callback) throws ManagedEntityRuntimeException {
        throw new NotImplementedException();
    }

    /**
     * Simple clone with identity callback
     *
     * @param <E> type of the entity
     * @return a managed entity that is a clone of this object
     * @throws ManagedEntityRuntimeException Managed Entity Runtime exception
     */
    default <E extends Manageable> E simpleClone() throws ManagedEntityRuntimeException {
        return clone(copiedEntity -> (E) copiedEntity);
    }

    /**
     * Copies an entity but does not save it in the database.
     *
     * @return a managed entity that is a copy of this object
     * @throws ManagedEntityRuntimeException Managed Entity Runtime exception
     */
    default <E extends Manageable> E copy() throws ManagedEntityRuntimeException {
        throw new NotImplementedException();
    }

    @FunctionalInterface
    interface CopiedEntityCallback<E extends Manageable> {
        E callback(Object copiedEntity);
    }
}
