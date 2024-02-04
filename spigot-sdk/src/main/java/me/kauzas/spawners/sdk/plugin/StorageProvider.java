package me.kauzas.spawners.sdk.plugin;

import org.hibernate.SessionFactory;

/**
 * Interface for storage providers.
 */
public interface StorageProvider {
    /**
     * Gets the session factory for the storage.
     *
     * @return SessionFactory instance.
     */
    SessionFactory getSessionFactory();

    /**
     * Initializes the storage.
     */
    void initStorage();
}
