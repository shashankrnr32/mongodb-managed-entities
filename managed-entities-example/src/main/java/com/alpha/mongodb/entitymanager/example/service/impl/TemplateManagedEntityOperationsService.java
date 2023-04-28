package com.alpha.mongodb.entitymanager.example.service.impl;

import com.alpha.mongodb.entitymanager.example.api.models.EntityDTO;
import com.alpha.mongodb.entitymanager.example.entity.TestEntity;
import com.alpha.mongodb.entitymanager.example.service.ManagedEntitiesOperationsService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TemplateManagedEntityOperationsService implements ManagedEntitiesOperationsService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Optional<EntityDTO> findById(String id) {
        Optional<TestEntity> optionalTestEntity = Optional.ofNullable(mongoTemplate.findById(new ObjectId(id), TestEntity.class));

        optionalTestEntity.ifPresent(testEntity -> {
            testEntity.setIndexedField(UUID.randomUUID().toString());
            testEntity.update();
            testEntity.refresh();
        });

        return optionalTestEntity.map(TestEntity::toDTO);
    }

    @Override
    public Optional<EntityDTO> clone(String id) {
        Optional<TestEntity> optionalTestEntity = Optional.ofNullable(mongoTemplate.findById(new ObjectId(id), TestEntity.class));

        if (optionalTestEntity.isPresent()) {
            TestEntity clonedEntity = (TestEntity) optionalTestEntity.get().clone(copiedEntity -> {
                TestEntity copied = (TestEntity) copiedEntity;
                copied.setIndexedField(UUID.randomUUID().toString());
                return copied;
            });
            return Optional.of(clonedEntity.toDTO());
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(String id) {
        Optional<TestEntity> optionalTestEntity = Optional.ofNullable(mongoTemplate.findById(new ObjectId(id), TestEntity.class));

        if (optionalTestEntity.isPresent()) {
            optionalTestEntity.get().delete();
            return true;
        }

        return false;
    }

    @Override
    public EntityDTO insert(EntityDTO entity) {
        return mongoTemplate.insert(entity.toEntity()).toDTO();
    }
}
