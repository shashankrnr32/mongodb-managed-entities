package com.alpha.mongodb.entitymanager.proxy.advice;

import com.alpha.mongodb.entitymanager.entity.Deletable;
import com.alpha.mongodb.entitymanager.utils.FieldUtils;
import lombok.SneakyThrows;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

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
        Optional<Field> idField = FieldUtils.getFieldWithAnnotation(invocation.getThis().getClass(), Id.class);
        idField.ifPresent(field -> FieldUtils.setFieldValue(field, invocation.getThis(), null));
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
