package com.alpha.mongodb.entitymanager.proxy.advice;

import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.Method;

public interface DelegatedAdvice<T> extends MethodInterceptor {

    Method[] getAdvisedMethods();

    Class<? extends T> getAdvisedClass();
}
