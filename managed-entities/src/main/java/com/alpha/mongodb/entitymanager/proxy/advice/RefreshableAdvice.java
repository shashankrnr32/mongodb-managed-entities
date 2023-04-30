package com.alpha.mongodb.entitymanager.proxy.advice;

import com.alpha.mongodb.entitymanager.entity.Refreshable;
import com.alpha.mongodb.entitymanager.exception.EntityNotFoundException;
import lombok.SneakyThrows;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.lang.reflect.Method;

/**
 * Refreshable advice that implements {@link Refreshable#refresh()} method
 *
 * @param <E>
 */
public class RefreshableAdvice<E> extends AbstractDelegatedAdvice<Refreshable, E> {

    public RefreshableAdvice(E entity, MongoTemplate mongoTemplate, String collectionName, ManageableAdvice<E> manageableAdvice) {
        super(entity, mongoTemplate, collectionName, manageableAdvice);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        validateAttachedEntity();
        if (invocation.getMethod().equals(getAdvisedClass().getMethod("refresh"))) {
            refresh(invocation);
            return null;
        } else {
            throw new NoSuchMethodException();
        }
    }

    private void refresh(MethodInvocation invocation) {
        Object id = getEntityInformation().getId(invocation.getThis());
        E refreshedEntity = (E) getMongoTemplate().findById(id, getEntity().getClass(), getCollectionName());

        if (null == refreshedEntity) {
            throw new EntityNotFoundException();
        }
        BeanUtils.copyProperties(refreshedEntity, invocation.getThis());
    }

    @Override
    @SneakyThrows
    public Method[] getAdvisedMethods() {
        return new Method[]{
                getAdvisedClass().getDeclaredMethod("refresh")
        };
    }

    @Override
    public Class<? extends Refreshable> getAdvisedClass() {
        return Refreshable.class;
    }


}
