package com.alpha.mongodb.entitymanager.proxy.advice;

import com.alpha.mongodb.entitymanager.entity.Manageable;
import lombok.SneakyThrows;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.lang.reflect.Method;

/**
 * Manageable Advice that implements {@link Manageable#isManaged()} and {@link Manageable#detach()} methods.
 *
 * @param <E> Entity Type
 * @author Shashank Sharma
 */
public class ManageableAdvice<E> extends AbstractDelegatedAdvice<Manageable, E> {

    private boolean managed = true;

    public ManageableAdvice(E entity, MongoTemplate mongoTemplate, String collectionName) {
        super(entity, mongoTemplate, collectionName, null);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (invocation.getMethod().equals(getAdvisedClass().getDeclaredMethod("isManaged"))) {
            return isManaged();
        } else if (invocation.getMethod().equals(getAdvisedClass().getDeclaredMethod("detach"))) {
            detach();
            return null;
        } else {
            throw new NoSuchMethodException();
        }
    }

    @Override
    protected void detach() {
        managed = false;
    }

    boolean isManaged() {
        return managed;
    }

    @Override
    @SneakyThrows
    public Method[] getAdvisedMethods() {
        return new Method[]{
                getAdvisedClass().getDeclaredMethod("isManaged"),
                getAdvisedClass().getDeclaredMethod("detach")
        };
    }

    @Override
    public Class<? extends Manageable> getAdvisedClass() {
        return Manageable.class;
    }


}
