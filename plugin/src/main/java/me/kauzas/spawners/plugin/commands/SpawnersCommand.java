package me.kauzas.spawners.plugin.commands;

import me.kauzas.spawners.plugin.commands.set.SetSpawnerCommand;
import me.kauzas.spawners.sdk.commands.*;

@CommandMeta(name = "spawners", description = "Main spawner command.")
public class SpawnersCommand extends AbstractCompositeCommand {
    public SpawnersCommand() {
        addSubCommand(new SetSpawnerCommand());
    }

    @Override
    public void main(CommandContext context) {
        context.sender().sendMessage("Main command executed.");
    }
}
