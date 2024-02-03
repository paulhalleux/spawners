package me.kauzas.spawners.sdk.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Utility class for maps.
 */
public class MapUtils {
    /**
     * Flattens a map and returns a new map with the flattened values.
     * Keys are represented as a path separated by dots.
     *
     * @param map Map to flatten
     * @return Flattened map
     */
    public static Map<String, Object> getFlattenedMap(Map<String, Object> map) {
        return getFlattenedMap(map, "");
    }

    /**
     * Flattens a map and returns a new map with the flattened values.
     *
     * @param map  Map to flatten
     * @param path Path of the map
     * @return Flattened map
     */
    private static Map<String, Object> getFlattenedMap(Map<String, Object> map, String path) {
        Map<String, Object> result = new LinkedHashMap<>();
        map.forEach((key, value) -> {
            if (value instanceof Map) {
                result.putAll(getFlattenedMap((Map<String, Object>) value, path + key + "."));
            } else {
                result.put(path + key, value);
            }
        });
        return result;
    }
}
