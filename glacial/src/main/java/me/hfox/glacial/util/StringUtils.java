package me.hfox.glacial.util;

public final class StringUtils {

    private StringUtils() {}

    private static final String SNAKE_CASE_SPLIT_REGEX = "(?<!^)(?<![A-Z])(?=[A-Z])";

    public static String toSnakeCase(String camelCase) {
        if (camelCase == null) {
            throw new NullPointerException();
        }

        String[] parts = camelCase.split(SNAKE_CASE_SPLIT_REGEX);

        StringBuilder builder = new StringBuilder(camelCase.length() + (parts.length - 1));

        for (int i = 0; i < parts.length - 1; i++) {
            builder.append(parts[i].toLowerCase()).append('_');
        }
        builder.append(parts[parts.length - 1].toLowerCase());

        return builder.toString();
    }

}
