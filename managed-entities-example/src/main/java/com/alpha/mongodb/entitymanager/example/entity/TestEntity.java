package com.alpha.mongodb.entitymanager.example.entity;

import com.alpha.mongodb.entitymanager.entity.Cloneable;
import com.alpha.mongodb.entitymanager.entity.Deletable;
import com.alpha.mongodb.entitymanager.entity.Refreshable;
import com.alpha.mongodb.entitymanager.entity.Updatable;
import com.alpha.mongodb.entitymanager.example.api.models.EntityDTO;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("TEST")
@Data
@FieldNameConstants
public class TestEntity implements Refreshable, Updatable, Cloneable, Deletable {

    @Id
    private String id;

    @Indexed(unique = true)
    private String indexedField;

    public EntityDTO toDTO() {
        EntityDTO entityDTO = new EntityDTO();
        entityDTO.setIndexedField(indexedField);
        entityDTO.setId(id);
        return entityDTO;
    }
}
