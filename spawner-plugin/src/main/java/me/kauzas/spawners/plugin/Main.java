package me.kauzas.spawners.plugin;


import me.kauzas.spawners.sdk.commands.CommandRegistry;
import me.kauzas.spawners.sdk.configuration.LocaleFile;
import me.kauzas.spawners.sdk.configuration.PluginFile;
import me.kauzas.spawners.sdk.events.DomainEventRegistry;
import me.kauzas.spawners.sdk.plugin.PluginBase;
import me.kauzas.spawners.sdk.plugin.StorageProvider;
import me.kauzas.spawners.sdk.storage.StorageFactory;
import me.kauzas.spawners.sdk.storage.StorageStrategy;
import me.kauzas.spawners.sdk.storage.StorageType;
import org.hibernate.SessionFactory;

/**
 * Entry point of the plugin.
 */
public class Main extends PluginBase implements StorageProvider {
    /**
     * Instance of the plugin.
     */
    private static Main instance = null;

    /**
     * Session factory for the database.
     */
    private SessionFactory sessionFactory;

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
    public void onPluginEnable() {
        // Add registries
        registry(new CommandRegistry("me.kauzas.spawners.plugin.commands", this));
        registry(new DomainEventRegistry("me.kauzas.spawners.plugin.handlers", this));

        // Add files
        PluginFile configFile = new PluginFile(this, "config.json");
        file("config", configFile);
        file("locale", new LocaleFile(this, configFile.get("locale"), "locales"));

        // Setup instance
        instance = this;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public void initStorage() {
        String storageType = getConfigFile().get("storage.type");
        StorageStrategy strategy = StorageFactory.getStorageStrategy(StorageType.fromString(storageType), this);
        sessionFactory = strategy.getSessionFactory("me.kauzas.spawners.plugin.entities", getConfigFile().get("storage.options"));
    }
}

