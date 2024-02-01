package me.kauzas.spawners.sdk.configuration;

import liqp.Template;
import liqp.TemplateParser;
import me.kauzas.spawners.sdk.plugin.PluginBase;
import org.bukkit.ChatColor;

import java.util.Map;

/**
 * Represents a locale file.
 * It is a {@link PluginFile} with a default path of "locales" and some default methods.
 */
public class LocaleFile extends PluginFile {
    /**
     * Creates a new locale file.
     *
     * @param plugin Plugin instance.
     * @param locale Name of the locale.
     * @param path   Path of the file.
     */
    public LocaleFile(PluginBase plugin, String locale, String path) {
        super(plugin, "%s.json".formatted(locale), path);
    }

    /**
     * Creates a new locale file.
     *
     * @param plugin Plugin instance.
     * @param locale Name of the locale.
     */
    public LocaleFile(PluginBase plugin, String locale) {
        super(plugin, locale, "locales");
    }

    /**
     * Gets a message from the locale file.
     *
     * @param key    Key of the message.
     * @param params Parameters of the message.
     * @return Message.
     */
    public String get(String key, Map<String, Object> params) {
        String value = get(key, key);
        TemplateParser parser = new TemplateParser.Builder().build();
        Template template = parser.parse(value);
        String rendered = template.render(params);
        return ChatColor.translateAlternateColorCodes('&', rendered);
    }

    /**
     * Gets a message from the locale file.
     *
     * @param key Key of the message.
     * @return Message.
     */
    public String get(String key) {
        return get(key, Map.of());
    }

    /**
     * Gets a message from the locale file with a prefix.
     *
     * @param key    Key of the message.
     * @param params Parameters of the message.
     * @return Message.
     */
    public String prefixed(String key, Map<String, Object> params) {
        return ChatColor.translateAlternateColorCodes('&', get("prefix", "")) + get(key, params);
    }

    /**
     * Gets a message from the locale file with a prefix.
     *
     * @param key Key of the message.
     * @return Message.
     */
    public String prefixed(String key) {
        return prefixed(key, Map.of());
    }
}
