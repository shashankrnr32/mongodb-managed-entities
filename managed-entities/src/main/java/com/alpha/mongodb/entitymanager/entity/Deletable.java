package com.alpha.mongodb.entitymanager.entity;

public interface Deletable extends Manageable {

    default void delete() {
    }
}
