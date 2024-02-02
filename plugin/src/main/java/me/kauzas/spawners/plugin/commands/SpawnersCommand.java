package me.kauzas.spawners.plugin.commands;

import me.kauzas.spawners.plugin.commands.config.ConfigCommand;
import me.kauzas.spawners.plugin.commands.set.SetSpawnerCommand;
import me.kauzas.spawners.sdk.commands.AbstractCompositeCommand;
import me.kauzas.spawners.sdk.commands.CommandContext;
import me.kauzas.spawners.sdk.commands.CommandMeta;

@CommandMeta(name = "spawners", description = "Main spawner command.")
public class SpawnersCommand extends AbstractCompositeCommand {
    public SpawnersCommand() {
        addSubCommand(new SetSpawnerCommand());
        addSubCommand(new ConfigCommand());
    }

    @Override
    public void main(CommandContext context) {
        showHelp(context);
    }
}
