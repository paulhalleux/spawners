package me.kauzas.spawners.sdk.registerer;

import me.kauzas.spawners.sdk.plugin.PluginBase;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Set;

/**
 * Registerer that uses reflection to register the object.
 *
 * @param <T> Type of the object to register.
 */
public abstract class ReflectionRegisterer<T> implements RegistererInterface<T> {
    private final Reflections reflections;
    private final PluginBase plugin;

    /**
     * Constructor of {@link ReflectionRegisterer} asking for the package.
     * Items in sub packages will be registered too.
     *
     * @param packagePath Path of the package.
     * @param plugin      Plugin instance.
     */
    public ReflectionRegisterer(String packagePath, PluginBase plugin) {
        this.reflections = new Reflections(packagePath);
        this.plugin = plugin;
    }

    /**
     * Get the plugin instance.
     *
     * @return Plugin instance.
     */
    public PluginBase getPlugin() {
        return plugin;
    }

    /**
     * Register automatically all the items from the
     * given package that match with the generic type.
     */
    @Override
    @SuppressWarnings({"rawtypes"})
    public void register() {
        try {
            Set<Class<? extends T>> items = reflections.getSubTypesOf((Class) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0]);

            for (Class<? extends T> t : items.stream().filter(o -> !o.isAnnotationPresent(SkipRegistration.class)).toList()) {
                T item = createInstance(t);
                if (item == null) {
                    onRegister(t, RegisterResult.FAILED);
                } else {
                    // If the item is already registered, skip it and call onRegister with ALREADY_REGISTERED
                    if (getRegisteredItems().contains(item)) {
                        onRegister((Class<? extends T>) item.getClass(), RegisterResult.ALREADY_REGISTERED);
                        continue;
                    }

                    RegisterResult registerResult = register(item);
                    if (registerResult == null) registerResult = RegisterResult.FAILED;
                    onRegister((Class<? extends T>) item.getClass(), registerResult);
                }
            }
        } catch (Exception ignored) {
        }
    }

    private T createInstance(Class<? extends T> t) {
        try {
            return t.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                 InvocationTargetException e) {
            try {
                return t.getDeclaredConstructor(JavaPlugin.class).newInstance(plugin);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException ex) {
                return null;
            }
        }
    }
}
