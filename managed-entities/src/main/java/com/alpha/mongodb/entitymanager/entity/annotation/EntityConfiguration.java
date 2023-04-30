package com.alpha.mongodb.entitymanager.entity.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Entity Configuration Annotation
 *
 * @author Shashank Sharma
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntityConfiguration {

    IdConfiguration id() default IdConfiguration.OBJECT_ID;

    enum IdConfiguration {
        OBJECT_ID,
        NONE
    }

}
