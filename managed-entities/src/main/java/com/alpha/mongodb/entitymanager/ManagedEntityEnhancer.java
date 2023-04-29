package com.alpha.mongodb.entitymanager;

import com.alpha.mongodb.entitymanager.proxy.MongoManagedEntityAdvice;
import lombok.SneakyThrows;
import org.springframework.aop.framework.ProxyFactory;

import java.util.Arrays;

public class ManagedEntityEnhancer {

    private final ManagedMongoTemplate mongoTemplate;

    public ManagedEntityEnhancer(ManagedMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @SneakyThrows
    public <T> T newEntity(Class<T> tClass) {
        T newEntity = tClass.getConstructor().newInstance();
        return enhance(newEntity);
    }

    public <T> T enhance(T entity) {
        return enhance(entity, this.mongoTemplate);
    }

    public <T> T enhance(T entity, String collectionName) {
        return enhance(entity, this.mongoTemplate, collectionName);
    }

    public static <T> T enhance(T entity, ManagedMongoTemplate mongoTemplate) {
        return enhance(entity, mongoTemplate, mongoTemplate.getCollectionName(entity.getClass()));
    }

    public static <T> T enhance(T entity, ManagedMongoTemplate mongoTemplate, String collectionName) {
        if (null != entity) {
            ProxyFactory proxyFactory = new ProxyFactory(entity);
            proxyFactory.addAdvice(new MongoManagedEntityAdvice<T>(
                    entity, mongoTemplate, collectionName));
            proxyFactory.setProxyTargetClass(true);
            Arrays.asList(entity.getClass().getInterfaces()).forEach(proxyFactory::addInterface);
            return (T) proxyFactory.getProxy();
        } else {
            return null;
        }
    }
}
