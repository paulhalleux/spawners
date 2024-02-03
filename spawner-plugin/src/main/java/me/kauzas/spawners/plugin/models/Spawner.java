package me.kauzas.spawners.plugin.models;

import org.bukkit.entity.EntityType;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Represents a spawner.
 */
public final class Spawner {
    /**
     * Identifier of the spawner.
     */
    private final UUID id;

    /**
     * Type of the entity to spawn.
     */
    private EntityType entityType;

    /**
     * Position of the spawner.
     */
    @Nullable
    private SpawnerPosition position;

    public Spawner(UUID id, EntityType entityType) {
        this.id = id;
        this.entityType = entityType;
    }

    /**
     * Get the identifier of the spawner.
     *
     * @return Identifier of the spawner.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Get the type of the entity to spawn.
     *
     * @return Type of the entity to spawn.
     */
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
     * Get the position of the spawner.
     *
     * @return Position of the spawner.
     */
    @Nullable
    public SpawnerPosition getPosition() {
        return position;
    }

    /**
     * Set the position of the spawner.
     *
     * @param position Position of the spawner.
     */
    public void setPosition(@Nullable SpawnerPosition position) {
        this.position = position;
    }
}
