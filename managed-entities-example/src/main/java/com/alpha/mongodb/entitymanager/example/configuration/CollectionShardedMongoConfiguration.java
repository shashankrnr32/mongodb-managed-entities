package com.alpha.mongodb.entitymanager.example.configuration;

import com.alpha.mongodb.entitymanager.ManagedMongoTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class CollectionShardedMongoConfiguration {

    private static final String SPRING_MONGO_DB_URI_COLLECTION_SHARDED = "spring.mongodb.uri";

    @Autowired
    private Environment environment;

    @Bean
    @Primary
    public MongoDatabaseFactory collectionShardedMongoDbFactory() {
        return new SimpleMongoClientDatabaseFactory(environment.getProperty(SPRING_MONGO_DB_URI_COLLECTION_SHARDED));
    }

    @Bean("collectionShardedMongoTemplate")
    @Primary
    public MongoTemplate collectionShardedMongoTemplate() {
        return new ManagedMongoTemplate(
                collectionShardedMongoDbFactory());
    }


}
