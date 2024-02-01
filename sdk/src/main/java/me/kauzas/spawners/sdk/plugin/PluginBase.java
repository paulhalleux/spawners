package me.kauzas.spawners.sdk.plugin;

import me.kauzas.spawners.sdk.configuration.PluginFile;
import me.kauzas.spawners.sdk.registerer.ReflectionRegisterer;
import me.kauzas.spawners.sdk.registerer.RegistererInterface;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Base class for plugins.
 */
public abstract class PluginBase extends JavaPlugin {
    private final List<RegistererInterface<?>> registerers;
    private final Map<String, PluginFile> files;

    /**
     * Constructor of {@link PluginBase}.
     */
    protected PluginBase() {
        this.registerers = new ArrayList<>();
        this.files = new HashMap<>();
    }

    /**
     * Gets a registerer by its class.
     *
     * @param registererClass Class of the registerer.
     * @return Registerer instance or null.
     */
    public ReflectionRegisterer<?> getRegisterer(Class<? extends RegistererInterface<?>> registererClass) {
        for (RegistererInterface<?> registerer : registerers) {
            if (registerer.getClass().equals(registererClass)) {
                return (ReflectionRegisterer<?>) registerer;
            }
        }
        return null;
    }

    /**
     * Registers a registerer.
     * Calls the consumer with the registerer as parameter.
     *
     * @param registerer Registerer to register.
     */
    public void addRegisterer(RegistererInterface<?> registerer, Consumer<RegistererInterface<?>> consumer) {
        registerers.add(registerer);
        consumer.accept(registerer);
    }

    /**
     * Registers a registerer.
     *
     * @param registerer Registerer to register.
     */
    public void addRegisterer(RegistererInterface<?> registerer) {
        addRegisterer(registerer, RegistererInterface::register);
    }

    /**
     * Gets all registered items.
     *
     * @return List of registered items.
     */
    public List<RegistererInterface<?>> getRegisterers() {
        return registerers;
    }

    /**
     * Gets a file by its name.
     *
     * @param name Name of the file.
     * @return File instance.
     */
    public PluginFile getFile(String name) {
        return files.get(name);
    }

    /**
     * Adds a file to the plugin.
     *
     * @param file File to add.
     */
    public void addFile(String name, PluginFile file) {
        files.put(name, file);
    }

    /**
     * Gets all files.
     *
     * @return List of files.
     */
    public List<PluginFile> getFiles() {
        return new ArrayList<>(files.values());
    }
}
