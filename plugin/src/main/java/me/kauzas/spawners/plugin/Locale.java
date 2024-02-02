package me.kauzas.spawners.plugin;

import java.util.Map;

/**
 * Represents the plugin locale.
 */
public class Locale {
    /**
     * Gets a message from the locale file.
     *
     * @param key    Key of the message
     * @param params Parameters of the message
     * @return Message
     */
    public static String get(String key, Map<String, Object> params) {
        return Main.getLocaleFile().get(key, params);
    }

    /**
     * Gets a message from the locale file.
     *
     * @param key Key of the message
     * @return Message
     */
    public static String get(String key) {
        return Main.getLocaleFile().get(key);
    }

    /**
     * Gets a message from the locale file with the plugin prefix.
     *
     * @param key    Key of the message
     * @param params Parameters of the message
     * @return Message
     */
    public static String prefixed(String key, Map<String, Object> params) {
        return Main.getLocaleFile().prefixed(key, params);
    }

    /**
     * Gets a message from the locale file with the plugin prefix.
     *
     * @param key Key of the message
     * @return Message
     */
    public static String prefixed(String key) {
        return Main.getLocaleFile().prefixed(key);
    }
}
