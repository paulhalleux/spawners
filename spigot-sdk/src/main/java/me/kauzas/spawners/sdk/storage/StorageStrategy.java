package me.kauzas.spawners.sdk.storage;

import me.kauzas.spawners.sdk.plugin.PluginBase;
import org.hibernate.SessionFactory;

import java.util.Map;

/**
 * Strategy for the storage.
 */
public abstract class StorageStrategy {
    /**
     * Plugin instance.
     */
    private final PluginBase plugin;

    /**
     * Constructor for the storage strategy.
     *
     * @param plugin Plugin instance.
     */
    protected StorageStrategy(PluginBase plugin) {
        this.plugin = plugin;
    }

    /**
     * Gets the plugin instance.
     *
     * @return Plugin instance.
     */
    public PluginBase getPlugin() {
        return plugin;
    }

    public abstract SessionFactory getSessionFactory(String entityPackage, Map<String, Object> properties);
}
