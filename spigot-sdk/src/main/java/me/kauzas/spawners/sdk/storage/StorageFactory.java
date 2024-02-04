package me.kauzas.spawners.sdk.storage;

import me.kauzas.spawners.sdk.plugin.PluginBase;
import me.kauzas.spawners.sdk.storage.strategies.SqliteStorageStrategy;

/**
 * Factory for the storage.
 */
public class StorageFactory {
    /**
     * Gets the storage strategy.
     *
     * @param type Type of the storage.
     * @return Storage strategy.
     */
    public static StorageStrategy getStorageStrategy(StorageType type, PluginBase plugin) {
        return switch (type) {
            case SQLITE -> new SqliteStorageStrategy(plugin);
            case MYSQL, SQLSERVER -> null;
        };
    }
}
