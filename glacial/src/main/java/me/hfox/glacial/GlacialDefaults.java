package me.hfox.glacial;

import me.hfox.glacial.annotation.Entity;
import me.hfox.glacial.exception.GlacialException;

public class GlacialDefaults {

    public static final String DEFAULT_COLLECTION_NAME = ".";

    public static Entity getEntity(Class<?> cls) {
        return getEntity(cls, false);
    }

    public static Entity getEntity(Class<?> cls, boolean require) {
        Entity entity;
        do {
            entity = cls.getAnnotation(Entity.class);
        } while (entity == null && (cls = cls.getSuperclass()) != Object.class);

        if (require && entity == null) {
            throw new GlacialException(cls.getSimpleName() + " does not have an @" + Entity.class.getSimpleName() + " Annotation");
        }

        return entity;
    }



}
