package com.alpha.mongodb.entitymanager.entity;

public interface Manageable {

    default boolean isManaged() {
        return false;
    }

    default void detach() {
    }
}
