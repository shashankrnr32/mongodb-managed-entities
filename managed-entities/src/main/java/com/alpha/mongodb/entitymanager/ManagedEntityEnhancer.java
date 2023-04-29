package com.alpha.mongodb.entitymanager;

import com.alpha.mongodb.entitymanager.entity.Manageable;
import com.alpha.mongodb.entitymanager.proxy.MongoManagedEntityAdvice;
import lombok.SneakyThrows;
import org.springframework.aop.framework.ProxyFactory;

import java.util.Arrays;

/**
 * Entity Enhancer that creates proxy entities and adds functionalities like
 * `refresh`, `update` based on the interfaces, the entity implements from.
 *
 * @author Shashank Sharma
 */
public class ManagedEntityEnhancer {

    private final ManagedMongoTemplate mongoTemplate;

    public ManagedEntityEnhancer(ManagedMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Creates a new entity of type `tClass`
     *
     * @param tClass the type of the object to be created
     * @param <T>    type
     * @return an enhanced entity of type T
     */
    @SneakyThrows
    public <T extends Manageable> T newEntity(Class<T> tClass) {
        T newEntity = tClass.getConstructor().newInstance();
        return enhance(newEntity);
    }

    /**
     * Enhances an existing {@link com.alpha.mongodb.entitymanager.entity.Manageable} entity
     * by providing a proxy implementation for all the manageable methods
     *
     * @param entity the entity to be enhanced
     * @param <T>    type
     * @return an enhanced entity of type T
     */
    public <T> T enhance(T entity) {
        return enhance(entity, this.mongoTemplate);
    }

    /**
     * Enhances an existing {@link com.alpha.mongodb.entitymanager.entity.Manageable} entity
     * by providing a proxy implementation for all the manageable methods
     *
     * @param entity         entity to be enhanced
     * @param collectionName collection name
     * @param <T>            type
     * @return an enhanced entity of type T
     */
    public <T> T enhance(T entity, String collectionName) {
        return enhance(entity, this.mongoTemplate, collectionName);
    }

    /**
     * Static implementation that enhances an existing {@link com.alpha.mongodb.entitymanager.entity.Manageable} entity
     * by providing a proxy implementation for all the manageable methods
     *
     * @param entity        entity to be enhanced
     * @param mongoTemplate Mongo template
     * @param <T>           type
     * @return enhanced entity of type T
     */
    public static <T> T enhance(T entity, ManagedMongoTemplate mongoTemplate) {
        return enhance(entity, mongoTemplate, mongoTemplate.getCollectionName(entity.getClass()));
    }

    /**
     * Static implementation that enhances an existing {@link com.alpha.mongodb.entitymanager.entity.Manageable} entity
     * by providing a proxy implementation for all the manageable methods
     *
     * @param entity        entity to be enhanced
     * @param mongoTemplate Mongo template
     * @param <T>           type
     * @return enhanced entity of type T
     */
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
