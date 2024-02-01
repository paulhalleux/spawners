package me.kauzas.spawners.plugin.events;

import me.kauzas.spawners.sdk.events.DomainEvent;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;

/**
 * Event called when a spawner type is changed.
 */
public class SpawnerTypeChangeEvent extends DomainEvent {
    /**
     * Handler list.
     */
    private static final HandlerList handlers = new HandlerList();

    /**
     * Player who changed the spawner type.
     */
    private final Player player;

    /**
     * Spawner state.
     */
    private final CreatureSpawner spawnerState;

    /**
     * Type of the entity to spawn.
     */
    private EntityType entityType;

    public SpawnerTypeChangeEvent(Player player, CreatureSpawner spawnerState, EntityType entityType) {
        this.player = player;
        this.spawnerState = spawnerState;
        this.entityType = entityType;
    }

    /**
     * Get the handler list.
     *
     * @return Handler list.
     */
    @Nonnull
    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Get the player who changed the spawner type.
     *
     * @return Player who changed the spawner type.
     */
    @Nonnull
    public Player getPlayer() {
        return player;
    }

    /**
     * Get the block of the spawner.
     *
     * @return Block of the spawner.
     */
    @Nonnull
    public CreatureSpawner getSpawnerState() {
        return spawnerState;
    }

    /**
     * Get the type of the entity to spawn.
     *
     * @return Type of the entity to spawn.
     */
    @Nonnull
    public EntityType getEntityType() {
        return entityType;
    }

    /**
     * Set the type of the entity to spawn.
     *
     * @param entityType Type of the entity to spawn.
     */
    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    /**
     * Get the handler list.
     *
     * @return Handler list.
     */
    @Override
    @Nonnull
    public HandlerList getHandlers() {
        return handlers;
    }
}
