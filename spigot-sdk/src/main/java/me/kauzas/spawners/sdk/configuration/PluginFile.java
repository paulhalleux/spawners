package me.kauzas.spawners.sdk.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.kauzas.spawners.sdk.plugin.PluginBase;
import me.kauzas.spawners.sdk.utils.MapUtils;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a plugin file.
 * It can be any file that is used by the plugin.
 */
public class PluginFile {
    /**
     * Gson instance.
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * File instance.
     */
    private final File file;

    /**
     * Path of the resource in the jar.
     */
    private final String resourcePath;

    /**
     * Map of the file content.
     * It is used to store the file content in memory.
     */
    private final Map<String, Object> map = new HashMap<>();

    /**
     * Plugin instance.
     */
    private final PluginBase plugin;

    /**
     * If the file has been saved.
     */
    private boolean saved;

    /**
     * Creates a new plugin file.
     *
     * @param plugin Plugin instance
     * @param name   Name of the file
     * @param path   Path of the file, default is the plugin data folder root
     */
    public PluginFile(PluginBase plugin, String name, String path) {
        String subPath = path == null || path.isEmpty() ? "" : (path.startsWith("/") ? path : ("/" + path));
        this.file = new File(plugin.getDataFolder().getAbsolutePath() + subPath, name);
        this.resourcePath = subPath + "/" + name;
        this.plugin = plugin;

        // Create file if it doesn't exist
        if (!this.file.exists()) {
            createDefaultFile();
        }

        // Load file content in memory
        reload();
    }

    /**
     * Creates a new plugin file.
     *
     * @param plugin Plugin instance
     * @param name   Name of the file
     */
    public PluginFile(PluginBase plugin, String name) {
        this(plugin, name, "");
    }

    /**
     * Gets the file instance.
     *
     * @return File instance
     */
    @Nonnull
    public File getFile() {
        return file;
    }

    /**
     * Gets the file content.
     *
     * @return File content
     */
    public String getFileContent() {
        return gson.toJson(map);
    }

    /**
     * Saves the file.
     */
    public void save() {
        final String json = getFileContent();
        try {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
            Files.write(file.toPath(), json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            setSaved(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets a value from the file
     * with a default value if the value is null.
     *
     * @param key Key of the value
     * @return Value
     */
    public <R> R get(String key, R defaultValue) {
        String[] keys = key.split("\\.");
        Object value = map.get(keys[0]);
        for (int i = 1; i < keys.length; i++) {
            if (value instanceof Map) {
                value = ((Map<?, ?>) value).get(keys[i]);
            } else {
                return defaultValue;
            }
        }
        return (R) (value != null ? value : defaultValue);
    }

    /**
     * Gets a value from the file.
     *
     * @param key Key of the value
     * @return Value
     */
    public <R> R get(String key) {
        return get(key, null);
    }

    /**
     * Sets a value in the file.
     *
     * @param key   Key of the value
     * @param value Value
     * @param save  If the file should be saved after setting the value
     */
    public void set(String key, Object value, boolean save) {
        String[] keys = key.split("\\.");
        Object lastValue = map.get(keys[0]);
        for (int i = 1; i < keys.length; i++) {
            if (lastValue instanceof Map) {
                lastValue = ((Map<?, ?>) lastValue).get(keys[i]);
            } else {
                return;
            }
        }
        ((Map<String, Object>) lastValue).put(keys[keys.length - 1], value);

        if (save) save();
        else setSaved(false);
    }

    /**
     * Sets a value in the file without saving it.
     *
     * @param key   Key of the value
     * @param value Value
     */
    public void set(String key, Object value) {
        set(key, value, false);
    }

    /**
     * Creates a default file.
     */
    private void createDefaultFile() {
        try (InputStream inputStream = getClass().getResourceAsStream(this.resourcePath)) {
            // Create file if it doesn't exist
            Files.createDirectories(this.file.getParentFile().toPath());
            Files.createFile(this.file.toPath());

            // Write default content
            String fileContent = inputStream != null ? new String(inputStream.readAllBytes()) : "{}";
            Files.write(this.file.toPath(), fileContent.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Restores the default file.
     */
    public void restoreDefaultFile() {
        //noinspection ResultOfMethodCallIgnored
        file.delete();
        createDefaultFile();
        reload();
    }

    /**
     * Reloads the file content.
     */
    public void reload() {
        try {
            this.map.clear();
            this.map.putAll(gson.fromJson(new FileReader(file), HashMap.class));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets if the file has been saved.
     *
     * @return If the file has been saved
     */
    public boolean isSaved() {
        return saved;
    }

    /**
     * Sets if the file has been saved.
     *
     * @param saved If the file has been saved
     */
    private void setSaved(boolean saved) {
        this.saved = saved;
    }

    /**
     * Gets the file content as a flat map.
     * Keys are separated by dots.
     *
     * @return Flat map
     */
    public Map<String, Object> getFlatMap() {
        return MapUtils.getFlattenedMap(map);
    }
}
