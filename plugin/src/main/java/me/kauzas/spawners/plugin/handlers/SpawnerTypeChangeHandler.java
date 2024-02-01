package me.kauzas.spawners.plugin.handlers;

import me.kauzas.spawners.plugin.events.SpawnerTypeChangeEvent;
import me.kauzas.spawners.sdk.events.DomainEventHandler;
import org.bukkit.block.CreatureSpawner;

/**
 * Handler for {@link SpawnerTypeChangeEvent} domain event.
 */
public class SpawnerTypeChangeHandler extends DomainEventHandler<SpawnerTypeChangeEvent> {
    @Override
    public void handle(SpawnerTypeChangeEvent event) {
        CreatureSpawner spawner = event.getSpawnerState();
        spawner.setSpawnedType(event.getEntityType());
        event.getPlayer().sendMessage("Spawner type changed to " + event.getEntityType().name());
    }
}
