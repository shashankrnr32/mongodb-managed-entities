package com.alpha.mongodb.entitymanager.entity;

public interface Updatable extends Manageable {

    default void update() {
    }
}
