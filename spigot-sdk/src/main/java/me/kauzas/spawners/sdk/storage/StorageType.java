package me.kauzas.spawners.sdk.storage;

/**
 * Enum that represents the type of storage that can be used
 * by the plugin.
 */
public enum StorageType {
    MYSQL,
    SQLITE,
    SQLSERVER;

    public static StorageType fromString(String type) {
        return switch (type.toUpperCase()) {
            case "MYSQL" -> MYSQL;
            case "SQLITE" -> SQLITE;
            case "SQLSERVER" -> SQLSERVER;
            default -> null;
        };
    }
}
