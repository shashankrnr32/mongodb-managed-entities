package com.alpha.mongodb.entitymanager.entity.info;

import com.alpha.mongodb.entitymanager.utils.FieldUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EntitySchemaInformation<E> {

    private static final String ID_FIELD = "_id";

    private static final String TRANSIENT_FIELD = "transient:%s";

    private static final String VERSION_FIELD = "_version";

    Map<String, IEntityField> entityFieldMap = new HashMap<>();

    public EntitySchemaInformation(Class<E> entityClass) {
        FieldUtils.getAllFields(entityClass).forEach(
                field -> {
                    IEntityField entityField = new EntityField(field);
                    if (field.isAnnotationPresent(Id.class)) {
                        entityFieldMap.put(ID_FIELD, entityField);
                    } else if (field.isAnnotationPresent(Transient.class)) {
                        entityFieldMap.put(String.format(TRANSIENT_FIELD, entityField.name()), entityField);
                    } else if (field.isAnnotationPresent(Version.class)) {
                        entityFieldMap.put(VERSION_FIELD, entityField);
                    } else {
                        entityFieldMap.put(entityField.name(), entityField);
                    }
                }
        );
    }

    public Optional<EntityField> getIdField() {
        return Optional.ofNullable((EntityField) entityFieldMap.get(ID_FIELD));
    }

    public Optional<EntityField> getVersionField() {
        return Optional.ofNullable((EntityField) entityFieldMap.get(VERSION_FIELD));
    }
}
