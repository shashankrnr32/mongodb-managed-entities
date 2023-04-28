package com.alpha.mongodb.entitymanager.proxy;

import com.alpha.mongodb.entitymanager.entity.Cloneable;
import com.alpha.mongodb.entitymanager.entity.Deletable;
import com.alpha.mongodb.entitymanager.entity.Manageable;
import com.alpha.mongodb.entitymanager.entity.Refreshable;
import com.alpha.mongodb.entitymanager.entity.Updatable;
import com.alpha.mongodb.entitymanager.exception.ManagedEntityInitializationException;
import com.alpha.mongodb.entitymanager.proxy.advice.CloneableAdvice;
import com.alpha.mongodb.entitymanager.proxy.advice.DelegatedAdvice;
import com.alpha.mongodb.entitymanager.proxy.advice.DeletableAdvice;
import com.alpha.mongodb.entitymanager.proxy.advice.ManageableAdvice;
import com.alpha.mongodb.entitymanager.proxy.advice.RefreshableAdvice;
import com.alpha.mongodb.entitymanager.proxy.advice.UpdatableAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MongoManagedEntityAdvice<E> implements MethodInterceptor {

    private final MongoTemplate mongoTemplate;
    private final String collectionName;
    private final E entity;

    private Map<Method, DelegatedAdvice<?>> adviceMap = new HashMap<>();

    public MongoManagedEntityAdvice(E entity, MongoTemplate mongoTemplate, String collectionName) {
        this.mongoTemplate = mongoTemplate;
        this.entity = entity;
        this.collectionName = collectionName;
        registerAdvices(entity);
    }

    private void registerAdvices(E entity) {
        if (!(entity instanceof Manageable)) {
            throw new ManagedEntityInitializationException();
        }
        ManageableAdvice<E> manageableAdvice = new ManageableAdvice<>(entity, mongoTemplate, collectionName);
        registerAdviceMethods(manageableAdvice);

        // Register Refreshable Advice
        if (entity instanceof Refreshable) {
            RefreshableAdvice<E> refreshableAdvice = new RefreshableAdvice<>(entity, mongoTemplate, collectionName, manageableAdvice);
            registerAdviceMethods(refreshableAdvice);
        }

        // Register Updatable
        if (entity instanceof Updatable) {
            UpdatableAdvice<E> updatableAdvice = new UpdatableAdvice<>(entity, mongoTemplate, collectionName, manageableAdvice);
            registerAdviceMethods(updatableAdvice);
        }

        // Register Cloneable
        if (entity instanceof Cloneable) {
            CloneableAdvice<E> cloneableAdvice = new CloneableAdvice<>(entity, mongoTemplate, collectionName, manageableAdvice);
            registerAdviceMethods(cloneableAdvice);
        }

        // Register Deletable
        if (entity instanceof Deletable) {
            DeletableAdvice<E> deletableAdvice = new DeletableAdvice<>(entity, mongoTemplate, collectionName, manageableAdvice);
            registerAdviceMethods(deletableAdvice);
        }
    }

    private void registerAdviceMethods(DelegatedAdvice<?> delegatedAdvice) {
        for (Method advisedMethod : delegatedAdvice.getAdvisedMethods()) {
            adviceMap.put(advisedMethod, delegatedAdvice);
        }
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        DelegatedAdvice<?> delegatedAdvice = adviceMap.get(invocation.getMethod());
        if (null != delegatedAdvice) {
            return delegatedAdvice.invoke(invocation);
        } else {
            return invocation.proceed();
        }
    }
}
