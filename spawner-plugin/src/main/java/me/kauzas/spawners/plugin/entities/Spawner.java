package me.kauzas.spawners.plugin.entities;

import jakarta.persistence.*;
import org.bukkit.entity.EntityType;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Represents a spawner.
 */
@Entity
public final class Spawner {
    /**
     * Identifier of the spawner.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Identifier of the owner of the spawner.
     */
    private UUID ownerId;

    /**
     * Type of the entity to spawn.
     */
    private EntityType entityType;

    /**
     * Position of the spawner.
     */
    @Nullable
    private String position;

    public Spawner(UUID ownerId, EntityType entityType, @Nullable String position) {
        this.ownerId = ownerId;
        this.entityType = entityType;
        this.position = position;
    }

    public Spawner() {
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
     * Get the identifier of the owner of the spawner.
     *
     * @return Identifier of the owner of the spawner.
     */
    public UUID getOwnerId() {
        return ownerId;
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
    public String getPosition() {
        return position;
    }

    /**
     * Set the position of the spawner.
     *
     * @param position Position of the spawner.
     */
    public void setPosition(@Nullable String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Spawner{id=%s, ownerId=%s, entityType=%s, position=%s}".formatted(id, ownerId, entityType, position);
    }
}
