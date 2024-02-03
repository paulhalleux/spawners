package me.kauzas.spawners.sdk.events;

import me.kauzas.spawners.sdk.plugin.PluginBase;
import me.kauzas.spawners.sdk.registry.ReflectionRegistry;
import me.kauzas.spawners.sdk.registry.RegisterResult;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Registerer for domain event handlers.
 */
@SuppressWarnings("rawtypes")
public class DomainEventRegistry extends ReflectionRegistry<DomainEventHandler> {
    /**
     * List of registered items.
     */
    private final List<DomainEventHandler> registeredItems;

    /**
     * Constructor of {@link DomainEventRegistry}.
     *
     * @param packagePath Package path to scan.
     * @param plugin      Plugin instance.
     */
    public DomainEventRegistry(String packagePath, PluginBase plugin) {
        super(packagePath, plugin);
        this.registeredItems = new ArrayList<>();
    }

    @Override
    public RegisterResult register(DomainEventHandler object) {
        Type type = getArgumentsType(object);

        if (type == null) {
            return RegisterResult.FAILED;
        }

        Bukkit.getServer().getPluginManager().registerEvent((Class<? extends Event>) type, object, getPriority(object), (listener, event) -> {
            object.handler((DomainEvent) event);
        }, getPlugin(), false);

        return RegisterResult.SUCCESS;
    }

    /**
     * Get the generic type of {@link DomainEventHandler}.
     *
     * @return Type of the arguments.
     */
    private Type getArgumentsType(DomainEventHandler handler) {
        Type superclass = handler.getClass().getGenericSuperclass();
        if (superclass instanceof Class) return null;
        return ((java.lang.reflect.ParameterizedType) superclass).getActualTypeArguments()[0];
    }

    /**
     * Get the priority of the handler.
     *
     * @param handler Handler to get the priority of.
     * @return Priority of the handler.
     */
    private EventPriority getPriority(DomainEventHandler handler) {
        Method[] methods = handler.getClass().getMethods();
        for (Method method : methods) {
            if (!method.getName().equals("handler")) {
                continue;
            }

            EventHandler eventHandler = method.getAnnotation(EventHandler.class);
            if (eventHandler != null) {
                return eventHandler.priority();
            }
        }
        return EventPriority.NORMAL;
    }

    @Override
    public List<DomainEventHandler> getRegisteredItems() {
        return registeredItems;
    }
}
