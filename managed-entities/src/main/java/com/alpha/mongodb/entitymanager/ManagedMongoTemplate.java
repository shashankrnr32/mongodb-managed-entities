package com.alpha.mongodb.entitymanager;

import com.alpha.mongodb.entitymanager.proxy.MongoManagedEntityAdvice;
import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.CursorPreparer;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;

import java.util.Arrays;

public class ManagedMongoTemplate extends MongoTemplate {
    public ManagedMongoTemplate(MongoClient mongoClient, String databaseName) {
        super(mongoClient, databaseName);
    }

    public ManagedMongoTemplate(MongoDatabaseFactory mongoDbFactory) {
        super(mongoDbFactory);
    }

    public ManagedMongoTemplate(MongoDatabaseFactory mongoDbFactory, MongoConverter mongoConverter) {
        super(mongoDbFactory, mongoConverter);
    }

    @Override
    protected <T> T doFindOne(String collectionName, Document query, Document fields, CursorPreparer preparer, Class<T> entityClass) {
        T result = super.doFindOne(collectionName, query, fields, preparer, entityClass);
        return enhance(result, collectionName);
    }

    private <T> T enhance(T t, String collectionName) {
        ProxyFactory proxyFactory = new ProxyFactory(t);
        proxyFactory.addAdvice(new MongoManagedEntityAdvice<T>(t, this, collectionName));
        proxyFactory.setProxyTargetClass(true);
        Arrays.asList(t.getClass().getInterfaces()).forEach(proxyFactory::addInterface);
        return (T) proxyFactory.getProxy();
    }
}
