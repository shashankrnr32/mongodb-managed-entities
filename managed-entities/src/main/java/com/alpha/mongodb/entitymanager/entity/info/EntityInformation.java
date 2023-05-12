package com.alpha.mongodb.entitymanager.entity.info;

import com.alpha.mongodb.entitymanager.entity.Versioned;
import com.alpha.mongodb.entitymanager.entity.annotation.EntityConfiguration;
import com.alpha.mongodb.entitymanager.entity.internal.ManageableWithId;
import com.alpha.mongodb.entitymanager.exception.ManagedEntityRuntimeException;
import com.alpha.mongodb.entitymanager.utils.FieldUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Tolerate;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Get Entity Information from the Entity class, or the entity object
 *
 * @param <E> Entity Type
 */
@RequiredArgsConstructor
public class EntityInformation<E> {

    private static final Map<Class<?>, EntitySchemaInformation<?>> SCHEMA_INFORMATION_MAP = new HashMap<>();

    @Setter
    public E entity;

    public final Class<E> entityClass;

    @Tolerate
    public EntityInformation(@NonNull E entity) {
        this.entity = entity;
        this.entityClass = (Class<E>) entity.getClass();
        SCHEMA_INFORMATION_MAP.computeIfAbsent(this.entityClass, EntitySchemaInformation::new);
    }

    /**
     * Get ID from the object
     *
     * @param entity Entity Object
     * @return ID
     */
    public Object getId(Object entity) {
        if (entity instanceof ManageableWithId) {
            return maybeToObjectId(((ManageableWithId) entity).getId());
        } else {
            Optional<Field> idField = FieldUtils.getFieldWithAnnotation(entity.getClass(), Id.class);
            if (idField.isPresent()) {
                return maybeToObjectId(FieldUtils.getFieldValue(idField.get(), entity));
            } else {
                throw new ManagedEntityRuntimeException("Unable to get Id from Entity");
            }
        }
    }

    /**
     * Get version of the entity
     *
     * @param entity Entity
     * @return Version
     */
    public Object getVersion(Object entity) {
        if (entity instanceof Versioned) {
            return ((Versioned) entity).getVersion();
        } else {
            Optional<Field> idField = FieldUtils.getFieldWithAnnotation(entity.getClass(), Version.class);
            if (idField.isPresent()) {
                return FieldUtils.getFieldValue(idField.get(), entity);
            } else {
                throw new ManagedEntityRuntimeException("Unable to get Id from Entity");
            }
        }
    }

    /**
     * Get the entity id from the set entity.
     *
     * @return ID
     */
    public Object getId() {
        return getId(entity);
    }

    /**
     * Get the entity version from the set entity.
     *
     * @return version
     */
    public Object getVersion() {
        return getVersion(entity);
    }

    private Object maybeToObjectId(Object id) {
        if (id instanceof String && getEntityConfiguration().id() == EntityConfiguration.IdConfiguration.OBJECT_ID) {
            return new ObjectId((String) id);
        }
        return id;
    }

    /**
     * Get the entity configuration if exists, gets a default configuration otherwise.
     *
     * @return Entity configuration
     */
    public EntityConfiguration getEntityConfiguration() {
        if (entityClass.isAnnotationPresent(EntityConfiguration.class)) {
            return entityClass.getDeclaredAnnotation(EntityConfiguration.class);
        } else {
            return DefaultEntityConfiguration.class.getDeclaredAnnotation(EntityConfiguration.class);
        }
    }

    public EntitySchemaInformation<E> getSchemaInformation() {
        return (EntitySchemaInformation<E>) SCHEMA_INFORMATION_MAP.get(this.entityClass);
    }

    @EntityConfiguration
    private static class DefaultEntityConfiguration {
    }

}
