package me.kauzas.spawners.plugin.commands.set;

import me.kauzas.spawners.sdk.commands.AbstractCommand;
import me.kauzas.spawners.sdk.commands.CommandContext;
import me.kauzas.spawners.sdk.commands.CommandMeta;
import me.kauzas.spawners.sdk.commands.CompletableCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.List;

@CommandMeta(name = "set", description = "Set a spawner type.", subCommand = true)
public class SetSpawnerCommand extends AbstractCommand<SetSpawnerCommandArguments> implements CompletableCommand {
    @Override
    public void execute(CommandContext context, SetSpawnerCommandArguments args) {
        context.sender().sendMessage("Set command executed. Entity type: %s.".formatted(args.getEntityType().getKey()));
    }

    @Override
    public TabCompleter complete() {
        return (sender, command, alias, args) -> {
            if (args.length == 1)
                return Arrays.stream(EntityType.values()).map(Enum::name)
                        .filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
                        .toList();
            return List.of();
        };
    }
}
