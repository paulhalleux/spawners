package me.kauzas.spawners.sdk.events;

import me.kauzas.spawners.sdk.plugin.PluginBase;
import me.kauzas.spawners.sdk.plugin.StorageProvider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.hibernate.Session;

import javax.annotation.Nullable;

/**
 * Abstract class for domain event handlers.
 *
 * @param <Event> Type of the event to handle.
 */
public abstract class DomainEventHandler<Event extends DomainEvent> implements Listener {
    /**
     * Bukkit event handler.
     * Has the lowest priority to be called last.
     *
     * @param event  Event to handle.
     * @param plugin Plugin instance.
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void handler(Event event, PluginBase plugin) {
        if (!event.isPropagated()) return;
        try {
            if (plugin instanceof StorageProvider provider) {
                provider.getSessionFactory().inTransaction(session -> handle(event, session));
            } else
                handle(event, null);
        } catch (Exception ignored) {
        }
    }

    /**
     * Custom event handler logic.
     *
     * @param event   Event to handle.
     * @param session Hibernate session.
     */
    protected abstract void handle(Event event, @Nullable Session session);
}
