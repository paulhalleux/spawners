package me.kauzas.spawners.sdk.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

/**
 * Abstract class for domain events.
 */
public abstract class DomainEvent extends Event {
    /**
     * Whether the event should be propagated.
     */
    private boolean propagate = true;

    /**
     * Call the event.
     *
     * @param event Event to call.
     */
    public static void call(DomainEvent event) {
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    /**
     * Call the event.
     */
    public void call() {
        DomainEvent.call(this);
    }

    /**
     * Check whether the event should be propagated.
     *
     * @return Whether the event should be propagated.
     */
    public boolean isPropagated() {
        return propagate;
    }

    /**
     * Set whether the event should be propagated.
     *
     * @param propagate Whether the event should be propagated.
     */
    public void setPropagate(boolean propagate) {
        this.propagate = propagate;
    }

    /**
     * Stop the propagation of the event.
     */
    public void stopPropagation() {
        this.propagate = false;
    }
}
