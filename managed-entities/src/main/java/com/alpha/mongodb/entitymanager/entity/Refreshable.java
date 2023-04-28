package com.alpha.mongodb.entitymanager.entity;

public interface Refreshable extends Manageable {

    default void refresh() {
    }

    String getId();
}
