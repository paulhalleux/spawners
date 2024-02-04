package me.kauzas.spawners.sdk.storage.strategies;

import jakarta.persistence.Entity;
import me.kauzas.spawners.sdk.plugin.PluginBase;
import me.kauzas.spawners.sdk.storage.StorageStrategy;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Strategy for the sqlite storage.
 */
public class SqliteStorageStrategy extends StorageStrategy {
    /**
     * Constructor for the storage strategy.
     *
     * @param plugin Plugin instance.
     */
    public SqliteStorageStrategy(PluginBase plugin) {
        super(plugin);
    }

    /**
     * Gets the entity classes from a package.
     *
     * @param entityPackage Package of the entities.
     * @return Array of entity classes.
     */
    private Class<?>[] getEntityClasses(String entityPackage) {
        Reflections reflection = new Reflections(entityPackage);
        return reflection.getTypesAnnotatedWith(Entity.class).toArray(Class<?>[]::new);
    }

    @Override
    public SessionFactory getSessionFactory(String entityPackage, Map<String, Object> properties) {
        Configuration configuration = new Configuration();

        configuration.setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC");
        configuration.setProperty("hibernate.connection.url", "jdbc:sqlite:%s/%s.db".formatted(getPlugin().getDataFolder().getAbsolutePath(), getPlugin().getName()));
        configuration.setProperty("hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect");
        configuration.setProperty("hibernate.show_sql", properties.getOrDefault("show_sql", false).toString());
        configuration.setProperty("hibernate.format_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");

        for (Class<?> entity : getEntityClasses(entityPackage)) {
            configuration.addAnnotatedClass(entity);
        }

        return configuration.buildSessionFactory();
    }
}
