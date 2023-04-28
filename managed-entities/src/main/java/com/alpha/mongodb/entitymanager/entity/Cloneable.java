package com.alpha.mongodb.entitymanager.entity;

import org.apache.commons.lang3.NotImplementedException;

public interface Cloneable extends Manageable {

    default <E extends Manageable> Manageable clone(CopiedEntityCallback<E> callback) {
        throw new NotImplementedException();
    }

    default Manageable simpleClone() {
        return clone(copiedEntity -> copiedEntity);
    }

    default Manageable copy() {
        throw new NotImplementedException();
    }

    @FunctionalInterface
    interface CopiedEntityCallback<E extends Manageable> {
        Manageable callback(E copiedEntity);
    }
}
