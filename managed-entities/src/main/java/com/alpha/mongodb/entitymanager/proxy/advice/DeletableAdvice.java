package com.alpha.mongodb.entitymanager.proxy.advice;

import com.alpha.mongodb.entitymanager.entity.Deletable;
import lombok.SneakyThrows;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.lang.reflect.Method;

public class DeletableAdvice<E> extends AbstractDelegatedAdvice<Deletable, E> {

    public DeletableAdvice(E entity, MongoTemplate mongoTemplate, String collectionName, ManageableAdvice<E> manageableAdvice) {
        super(entity, mongoTemplate, collectionName, manageableAdvice);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        validateAttachedEntity();
        if (invocation.getMethod().equals(getAdvisedClass().getMethod("delete"))) {
            delete(invocation);
            return null;
        } else {
            throw new NoSuchMethodException();
        }
    }

    private void delete(MethodInvocation invocation) {
        getMongoTemplate().remove(invocation.getThis(), getCollectionName());
        detach();
    }

    @Override
    @SneakyThrows
    public Method[] getAdvisedMethods() {
        return new Method[]{
                getAdvisedClass().getDeclaredMethod("delete")
        };
    }

    @Override
    public Class<? extends Deletable> getAdvisedClass() {
        return Deletable.class;
    }


}
