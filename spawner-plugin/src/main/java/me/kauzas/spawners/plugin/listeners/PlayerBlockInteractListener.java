package me.kauzas.spawners.plugin.listeners;

import me.kauzas.spawners.plugin.events.SpawnerPickupEvent;
import me.kauzas.spawners.sdk.events.DomainEvent;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Listener for player block interactions.
 */
public class PlayerBlockInteractListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        boolean isSneaking = event.getPlayer().isSneaking();
        Block clickedBlock = event.getClickedBlock();
        Action action = event.getAction();

        if (isSneaking && action == Action.RIGHT_CLICK_BLOCK) {
            assert clickedBlock != null;
            if (clickedBlock.getState() instanceof CreatureSpawner spawner) {
                SpawnerPickupEvent pickupEvent = new SpawnerPickupEvent(event.getPlayer(), spawner, clickedBlock);
                DomainEvent.call(pickupEvent);
            }
        }
    }
}
