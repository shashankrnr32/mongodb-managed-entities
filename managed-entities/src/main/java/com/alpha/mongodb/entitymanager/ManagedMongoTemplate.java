package com.alpha.mongodb.entitymanager;

import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.CursorPreparer;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.convert.MongoWriter;

/**
 * Managed Mongo Template to create proxy entities for queries and inserts made to the
 * database. This extends the functionality of {@link MongoTemplate} and creates enhanced entity objects
 * for query and inserted objects.
 *
 * @author Shashank Sharma
 */
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
        return ManagedEntityEnhancer.enhance(result, this, collectionName);
    }

    @Override
    protected <T> T doInsert(String collectionName, T objectToSave, MongoWriter<T> writer) {
        T result = super.doInsert(collectionName, objectToSave, writer);
        return ManagedEntityEnhancer.enhance(result, this, collectionName);
    }
}
