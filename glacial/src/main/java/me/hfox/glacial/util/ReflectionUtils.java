package me.hfox.glacial.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtils {

    public static List<Field> getFields(Class<?> cls) {
        return getFields(cls, true);
    }

    public static List<Field> getFields(Class<?> cls, boolean parent) {
        List<Field> fields = new ArrayList<>();
        do {
            fields.addAll(Arrays.asList(cls.getDeclaredFields()));
            cls = cls.getSuperclass();
        } while (parent && cls != Object.class && cls != null);

        return fields;
    }

}
