package com.alpha.mongodb.entitymanager.proxy.advice;

import com.alpha.mongodb.entitymanager.exception.EntityDetachedException;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.Nullable;

/**
 * Abstract class for Delegated Advices for Manageable Entities
 *
 * @param <T> The manageable interface
 * @param <E> The entity class
 */
public abstract class AbstractDelegatedAdvice<T, E> implements DelegatedAdvice<T> {

    @Getter(AccessLevel.PROTECTED)
    private final MongoTemplate mongoTemplate;

    @Getter(AccessLevel.PROTECTED)
    private final String collectionName;

    @Getter(AccessLevel.PROTECTED)
    private final E entity;

    @Getter(AccessLevel.PROTECTED)
    private final ManageableAdvice<E> manageableAdvice;

    AbstractDelegatedAdvice(E entity, MongoTemplate mongoTemplate, String collectionName,
                            @Nullable ManageableAdvice<E> manageableAdvice) {
        this.entity = entity;
        this.mongoTemplate = mongoTemplate;
        this.collectionName = collectionName;
        this.manageableAdvice = manageableAdvice;

        if (!validateEntity()) {
            throw new IllegalStateException(String.format("Unable to register the delegated advice: %s", this.getClass().getName()));
        }
    }

    void validateAttachedEntity() {
        if (!manageableAdvice.isManaged()) {
            throw new EntityDetachedException();
        }
    }

    protected void detach() {
        this.getManageableAdvice().detach();
    }

    protected boolean validateEntity() {
        return getAdvisedClass().isInstance(entity);
    }
}
