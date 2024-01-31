package me.kauzas.spawners.plugin;

import me.kauzas.spawners.sdk.commands.CommandRegisterer;
import me.kauzas.spawners.sdk.events.DomainEventRegisterer;
import me.kauzas.spawners.sdk.plugin.PluginBase;

/**
 * Entry point of the plugin.
 */
public class Main extends PluginBase {
    @Override
    public void onEnable() {
        getLogger().info("Plugin enabled.");

        // Add registerers
        addRegisterer(new CommandRegisterer("me.kauzas.spawners.plugin.commands", this));
        addRegisterer(new DomainEventRegisterer("me.kauzas.spawners.plugin.handlers", this));
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled.");
    }
}