package com.alpha.mongodb.entitymanager.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.collections.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Utility class to operate on fields on a class.
 */
@UtilityClass
public class FieldUtils {

    public static <T> List<Field> getAllFields(T t) {
        return getAllFields(t.getClass());
    }

    public static <T> List<Field> getAllFields(Class<T> klass) {
        List<Field> fields = new ArrayList<>();
        Class<?> operatingKlass = klass;

        while (operatingKlass != Object.class) {
            fields.addAll(Arrays.asList(operatingKlass.getDeclaredFields()));
            operatingKlass = operatingKlass.getSuperclass();
        }
        return fields;
    }

    public static <T, S extends Annotation> List<Field> getAllFieldsWithAnnotation(T t, Class<S> annotationKlass) {
        return getAllFieldsWithAnnotation(t.getClass(), annotationKlass);
    }

    public static <T, S extends Annotation> List<Field> getAllFieldsWithAnnotation(Class<T> klass, Class<S> annotationKlass) {
        List<Field> fields = new ArrayList<>();
        Class<?> operatingKlass = klass;

        while (operatingKlass != Object.class) {
            fields.addAll(Arrays.stream(operatingKlass.getDeclaredFields()).filter(
                    field -> field.getAnnotationsByType(annotationKlass).length != 0).collect(Collectors.toList()));
            operatingKlass = operatingKlass.getSuperclass();
        }
        return fields;
    }

    public static <T, S extends Annotation> Field getFieldWithAnnotation(T t, Class<S> annotationKlass) {
        return getAllFieldsWithAnnotation(t.getClass(), annotationKlass).get(0);
    }

    public static <T, S extends Annotation> Optional<Field> getFieldWithAnnotation(Class<T> klass, Class<S> annotationKlass) {
        List<Field> fields = getAllFieldsWithAnnotation(klass, annotationKlass);
        if (CollectionUtils.isEmpty(fields)) {
            return Optional.empty();
        } else {
            return Optional.of(fields.get(0));
        }
    }

    @SneakyThrows
    public static Object getFieldValue(Field field, Object o) {
        field.setAccessible(true);
        return field.get(o);
    }

    @SneakyThrows
    public static void setFieldValue(Field field, Object o, final Object value) {
        field.setAccessible(true);
        field.set(o, value);
    }

}
