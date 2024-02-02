package me.kauzas.spawners.plugin.handlers;

import me.kauzas.spawners.plugin.Locale;
import me.kauzas.spawners.plugin.events.SpawnerTypeChangeEvent;
import me.kauzas.spawners.sdk.events.DomainEventHandler;
import org.bukkit.block.CreatureSpawner;

import java.util.Map;

/**
 * Handler for {@link SpawnerTypeChangeEvent} domain event.
 */
public class SpawnerTypeChangeHandler extends DomainEventHandler<SpawnerTypeChangeEvent> {
    @Override
    public void handle(SpawnerTypeChangeEvent event) {
        CreatureSpawner spawner = event.getSpawnerState();
        spawner.setSpawnedType(event.getEntityType());
        spawner.update();
        event.getPlayer().sendMessage(Locale.prefixed("spawner-type-changed", Map.of("entity", event.getEntityType().name())));
    }
}
