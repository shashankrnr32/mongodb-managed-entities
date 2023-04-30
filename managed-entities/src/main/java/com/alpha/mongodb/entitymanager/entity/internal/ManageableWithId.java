package com.alpha.mongodb.entitymanager.entity.internal;

import com.alpha.mongodb.entitymanager.entity.Manageable;

/**
 * Entity that has a getter method that returns id. Not to be used by external users
 *
 * @author Shashank Sharma
 */
public interface ManageableWithId extends Manageable {

    Object getId();
}
