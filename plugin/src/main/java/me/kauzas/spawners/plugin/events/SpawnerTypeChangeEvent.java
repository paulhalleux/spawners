package me.kauzas.spawners.plugin.events;

import me.kauzas.spawners.sdk.events.DomainEvent;
import org.bukkit.block.Block;
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
     * Block of the spawner.
     */
    private Block block;

    /**
     * Type of the entity to spawn.
     */
    private EntityType entityType;

    public SpawnerTypeChangeEvent(Player player, Block block, EntityType entityType) {
        this.player = player;
        this.block = block;
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
    public Block getBlock() {
        return block;
    }

    /**
     * Set the block of the spawner.
     *
     * @param block Block of the spawner.
     */
    public void setBlock(Block block) {
        this.block = block;
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
