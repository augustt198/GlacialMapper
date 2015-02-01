package me.hfox.glacial.options.name;

import me.hfox.glacial.GlacialDefaults;
import me.hfox.glacial.annotation.Entity;
import me.hfox.glacial.exception.GlacialException;

public class DefaultNameCreator implements NameCreator {

    /**
     * Whether or not the @{@link me.hfox.glacial.annotation.Entity} naming defaults to snake case
     */
    private boolean snakeCase = true;

    public boolean isSnakeCase() {
        return snakeCase;
    }

    public void setSnakeCase(boolean snakeCase) {
        this.snakeCase = snakeCase;
    }

    @Override
    public String create(Class<?> cls) {
        Class<?> original = cls;

        Entity entity = null;
        do {
            entity = cls.getAnnotation(Entity.class);
        } while (entity == null && (cls = cls.getSuperclass()) != Object.class);

        if (entity == null) {
            throw new GlacialException(cls.getSimpleName() + " does not have an @" + Entity.class.getSimpleName() + " Annotation");
        }

        return create(entity, cls);
    }

    @Override
    public String create(Entity entity, Class<?> cls) {
        if (entity.value() != null && !entity.value().equals(GlacialDefaults.DEFAULT_COLLECTION_NAME)) {
            return entity.value();
        }

        if (snakeCase) {
            StringBuilder builder = new StringBuilder();
            char[] name = cls.getSimpleName().toCharArray();

            for (char ch : name) {
                if (Character.isUpperCase(ch)) {
                    ch = Character.toLowerCase(ch);
                    builder.append("_");
                }

                builder.append(ch);
            }

            return builder.toString();
        }

        return cls.getSimpleName().toLowerCase();
    }

}
