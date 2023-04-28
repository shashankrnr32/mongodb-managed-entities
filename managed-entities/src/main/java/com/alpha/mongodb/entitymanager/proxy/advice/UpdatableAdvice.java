package com.alpha.mongodb.entitymanager.proxy.advice;

import com.alpha.mongodb.entitymanager.entity.Updatable;
import lombok.SneakyThrows;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.lang.reflect.Method;

public class UpdatableAdvice<E> extends AbstractDelegatedAdvice<Updatable, E> {

    public UpdatableAdvice(E entity, MongoTemplate mongoTemplate, String collectionName,
                           ManageableAdvice<E> manageableAdvice) {
        super(entity, mongoTemplate, collectionName, manageableAdvice);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        validateAttachedEntity();
        if (invocation.getMethod().equals(getAdvisedClass().getMethod("update"))) {
            update(invocation);
            return null;
        } else {
            throw new NoSuchMethodException();
        }
    }

    private void update(MethodInvocation invocation) {
        E updatedEntity = (E) getMongoTemplate().save(invocation.getThis(), getCollectionName());
        BeanUtils.copyProperties(updatedEntity, invocation.getThis());
    }

    @Override
    @SneakyThrows
    public Method[] getAdvisedMethods() {
        return new Method[]{
                getAdvisedClass().getDeclaredMethod("update")
        };
    }

    @Override
    public Class<? extends Updatable> getAdvisedClass() {
        return Updatable.class;
    }


}
