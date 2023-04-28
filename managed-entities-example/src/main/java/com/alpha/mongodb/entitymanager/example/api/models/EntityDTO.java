package com.alpha.mongodb.entitymanager.example.api.models;

import com.alpha.mongodb.entitymanager.example.entity.TestEntity;
import lombok.Data;

@Data
public class EntityDTO {
    private String id;
    private String indexedField;

    public TestEntity toEntity() {
        TestEntity entity = new TestEntity();
        entity.setIndexedField(indexedField);
        return entity;
    }
}
