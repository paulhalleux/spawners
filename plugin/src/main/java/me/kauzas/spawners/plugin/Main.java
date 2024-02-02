package me.kauzas.spawners.plugin;

import me.kauzas.spawners.sdk.commands.CommandRegisterer;
import me.kauzas.spawners.sdk.configuration.LocaleFile;
import me.kauzas.spawners.sdk.configuration.PluginFile;
import me.kauzas.spawners.sdk.events.DomainEventRegisterer;
import me.kauzas.spawners.sdk.plugin.PluginBase;

/**
 * Entry point of the plugin.
 */
public class Main extends PluginBase {
    /**
     * Instance of the plugin.
     */
    private static Main instance = null;

    /**
     * Gets the instance of the plugin.
     *
     * @return Plugin instance.
     */
    public static Main getInstance() {
        return instance;
    }

    /**
     * Gets the config file.
     *
     * @return Config file.
     */
    public static PluginFile getConfigFile() {
        return instance.getFile("config");
    }

    /**
     * Gets the locale file.
     *
     * @return Locale file.
     */
    public static LocaleFile getLocaleFile() {
        return (LocaleFile) instance.getFile("locale");
    }

    @Override
    public void onEnable() {
        getLogger().info("Plugin enabled.");

        // Add registerers
        addRegisterer(new CommandRegisterer("me.kauzas.spawners.plugin.commands", this));
        addRegisterer(new DomainEventRegisterer("me.kauzas.spawners.plugin.handlers", this));

        // Add files
        PluginFile configFile = new PluginFile(this, "config.json");
        LocaleFile localeFile = new LocaleFile(this, configFile.get("locale"), "locales");

        // Add files to map
        addFile("config", configFile);
        addFile("locale", localeFile);

        instance = this;
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled.");
    }
}

