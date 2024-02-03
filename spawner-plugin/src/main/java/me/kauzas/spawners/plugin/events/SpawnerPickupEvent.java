package me.kauzas.spawners.plugin.events;

import me.kauzas.spawners.sdk.events.DomainEvent;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;

public final class SpawnerPickupEvent extends DomainEvent {
    /**
     * Handler list.
     */
    private static final HandlerList handlers = new HandlerList();

    /**
     * Player who changed the spawner type.
     */
    @Nonnull
    private final Player player;

    /**
     * Spawner state.
     */
    @Nonnull
    private final CreatureSpawner spawner;

    /**
     * Block of the spawner.
     */
    @Nonnull
    private final Block block;


    public SpawnerPickupEvent(@Nonnull Player player, @Nonnull CreatureSpawner spawner, @Nonnull Block block) {
        this.player = player;
        this.spawner = spawner;
        this.block = block;
    }

    /**
     * Get the handler list.
     *
     * @return Handler list.
     */
    public static HandlerList getHandlerList() {
        return handlers;
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
    public CreatureSpawner getSpawner() {
        return spawner;
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
}
