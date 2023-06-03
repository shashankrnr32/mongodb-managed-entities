package com.alpha.mongodb.entitymanager.entity.info;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.lang.reflect.Field;

public class EntityField implements IEntityField {

    private final String fieldName;

    @Getter
    private final Field field;

    public EntityField(Field field) {
        this.field = field;
        if (field.isAnnotationPresent(org.springframework.data.mongodb.core.mapping.Field.class)) {
            org.springframework.data.mongodb.core.mapping.Field mongoFieldConfig =
                    field.getAnnotation(org.springframework.data.mongodb.core.mapping.Field.class);
            if (StringUtils.isBlank(mongoFieldConfig.name())) {
                fieldName = mongoFieldConfig.name();
            } else {
                fieldName = field.getName();
            }
        } else {
            fieldName = field.getName();
        }
    }

    @Override
    public String name() {
        return fieldName;
    }

    @Override
    public boolean isId() {
        return field.isAnnotationPresent(Id.class);
    }

    @Override
    public boolean isTransient() {
        return field.isAnnotationPresent(Transient.class);
    }
}
