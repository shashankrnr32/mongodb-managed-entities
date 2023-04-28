package com.alpha.mongodb.entitymanager.proxy.advice;

import com.alpha.mongodb.entitymanager.entity.Cloneable;
import com.alpha.mongodb.entitymanager.utils.FieldUtils;
import lombok.SneakyThrows;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.lang.reflect.Method;

public class CloneableAdvice<E> extends AbstractDelegatedAdvice<Cloneable, E> {

    public CloneableAdvice(E entity, MongoTemplate mongoTemplate, String collectionName, ManageableAdvice<E> manageableAdvice) {
        super(entity, mongoTemplate, collectionName, manageableAdvice);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        validateAttachedEntity();
        if (invocation.getMethod().equals(getAdvisedClass().getMethod("clone", Cloneable.CopiedEntityCallback.class))) {
            return doClone(invocation);
        } else if (invocation.getMethod().equals(getAdvisedClass().getMethod("copy"))) {
            return copy(invocation);
        } else {
            throw new NoSuchMethodException();
        }
    }

    @SneakyThrows
    private E doClone(MethodInvocation invocation) {
        E copiedEntity = copy(invocation);
        return getMongoTemplate().insert(copiedEntity);
    }

    @SneakyThrows
    private E copy(MethodInvocation invocation) {
        E copiedEntity = (E) invocation.getThis().getClass().getConstructor().newInstance();
        BeanUtils.copyProperties(invocation.getThis(), copiedEntity);
        FieldUtils.setFieldValue(FieldUtils.getFieldWithAnnotation(invocation.getThis(), Id.class), copiedEntity, null);
        return copiedEntity;
    }

    @Override
    @SneakyThrows
    public Method[] getAdvisedMethods() {
        return new Method[]{
                getAdvisedClass().getDeclaredMethod("clone", Cloneable.CopiedEntityCallback.class),
                getAdvisedClass().getDeclaredMethod("copy")
        };
    }

    @Override
    public Class<? extends Cloneable> getAdvisedClass() {
        return Cloneable.class;
    }


}
