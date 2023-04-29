package com.alpha.mongodb.entitymanager.proxy.advice;

import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.Method;

/**
 * The delegated advice interface
 *
 * @param <T> Manageable Class that is implementing the entity
 */
public interface DelegatedAdvice<T> extends MethodInterceptor {

    Method[] getAdvisedMethods();

    Class<? extends T> getAdvisedClass();
}
