package me.kauzas.spawners.sdk.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Utility class for reflection.
 */
public class ReflectionUtils {
    /**
     * Get all fields of a class that are annotated with the specified annotation.
     *
     * @param clazz      Class to get the fields from.
     * @param annotation Annotation to filter the fields.
     * @return Array of fields that are annotated with the specified annotation.
     */
    public static Field[] getFieldsWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        Field[] fields = clazz.getDeclaredFields();
        return Arrays.stream(fields).filter(field -> field.isAnnotationPresent(annotation)).toArray(Field[]::new);
    }
}
