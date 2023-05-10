package exercise;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

// BEGIN
public class Validator {
    public static List<String> validate(Object obj) {
        return Arrays.stream(
                        obj.getClass().getDeclaredFields()
                ).filter(f -> f.isAnnotationPresent(NotNull.class))
                .filter(f -> {
                    try {
                        f.setAccessible(true);
                        return f.get(obj) == null;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(f -> f.getName())
                .toList();
    }

    private static String NotNullValidate(NotNull a, Object value) {
        return value == null ? "can not be null" : null;
    }

    private static String MinLengthValidate(MinLength a, Object value) {
        if (value instanceof String) {
            if (((String) value).length() < a.minLength()) {
                return "length less than 4";
            }
        }
        return null;
    }

    private static List<String> fieldValidate(Field f, Object value) {
        return Arrays.stream(f.getAnnotations())
                .map(a -> {
                    if (a.annotationType() == NotNull.class) {
                        return NotNullValidate(f.getAnnotation(NotNull.class), value);
                    } else if (a.annotationType() == MinLength.class) {
                        return MinLengthValidate(f.getAnnotation(MinLength.class), value);
                    }
                    return null;
                })
                .filter(v -> Objects.nonNull(v))
                .toList();
    }

    public static Map<String, List<String>> advancedValidate(Object obj) {
        var map = new HashMap<String, List<String>>();

        Arrays.stream(
                        obj.getClass().getDeclaredFields()
                ).filter(f -> f.isAnnotationPresent(NotNull.class) || f.isAnnotationPresent(MinLength.class))
                .forEach(f -> {
                    Object value;
                    try {
                        f.setAccessible(true);
                        value = f.get(obj);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    var result = fieldValidate(f, value);

                    if (!result.isEmpty()) {
                        map.put(f.getName(),result);
                    }
                });

        return map;
    }
}
// END
