package com.lasmch.util;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public interface BeanUtil {


    static Map copyToMap(Object t) {
        return copyToMap(t, new HashMap<String, Object>(), true, false);
    }

    static Map copyToMap(Object t, String... keys) {
        return copyToMap(t, new HashMap<String, Object>(), true, false, keys);
    }

    static Map copyToMap(Object t, boolean underscore, boolean nullable, String... keys) {
        return copyToMap(t, new HashMap<String, Object>(), underscore, nullable, keys);
    }


    static Map copyToMap(Object t, boolean underscore, boolean nullable) {
        return copyToMap(t, new HashMap<String, Object>(), underscore, nullable);
    }

    static Map copyToMap(Object t, Map map) {
        return copyToMap(t, map, true, false);
    }

    static Map copyToMap(final Object t, final Map map, boolean isUnderscore, boolean nullAllow) {

        if (t instanceof Map) {
            Map<String, Object> pmap = (Map) t;

            pmap.entrySet().stream()
                    .filter(i -> {
                        if (nullAllow)
                            return true;
                        else
                            return i.getValue() != null;
                    })
                    .collect(Collectors.toMap(
                            m -> isUnderscore ? toUnderscore(m.getKey()) : m.getKey(),
                            m -> m.getValue()
                    )).entrySet().stream()
                    .forEach(i -> map.put(i.getKey(), i.getValue()));

        } else {

            for (Field f : t.getClass().getDeclaredFields()) {
                ReflectionUtils.makeAccessible(f);
                Object value = ReflectionUtils.getField(f, t);
                if (nullAllow)
                    map.put(isUnderscore ? toUnderscore(f.getName()) : f.getName(), value);
                else {
                    if (value != null)
                        map.put(isUnderscore ? toUnderscore(f.getName()) : f.getName(), value);
                }
            }

        }
        return map;


    }

    static Map copyToMap(final Object t, final Map map, boolean isUnderscore, boolean nullAllow, String... keys) {
        Map amap = copyToMap(t, map, isUnderscore, nullAllow);
        for (String k : keys) {
            amap.remove(k);
        }
        return amap;
    }


    static String toUnderscore(String str) {
        if (str == null) {
            return null;
        }

        if (str.trim().length() == 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if (Character.isUpperCase(c) && i > 0) {
                sb.append("_" + Character.toLowerCase(c));
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }
}
