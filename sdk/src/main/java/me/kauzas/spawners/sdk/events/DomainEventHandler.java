package me.kauzas.spawners.sdk.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

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
     * @param event Event to handle.
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void handler(Event event) {
        if (!event.isPropagated()) return;
        try {
            handle(event);
        } catch (Exception ignored) {
        }
    }

    /**
     * Custom event handler logic.
     *
     * @param event Event to handle.
     */
    protected abstract void handle(Event event);
}
