package com.alpha.mongodb.entitymanager.entity;

/**
 * An entity that contains version
 *
 * @author Shashank Sharma
 */
public interface Versioned extends Manageable {

    Object getVersion();
}
