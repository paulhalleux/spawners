package me.kauzas.spawners.plugin.commands.set;

import me.kauzas.spawners.plugin.events.SpawnerTypeChangeEvent;
import me.kauzas.spawners.sdk.commands.AbstractCommand;
import me.kauzas.spawners.sdk.commands.CommandContext;
import me.kauzas.spawners.sdk.commands.CommandMeta;
import me.kauzas.spawners.sdk.commands.CompletableCommand;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

@CommandMeta(name = "set", description = "Set a spawner type.", subCommand = true, playerOnly = true)
public class SetSpawnerCommand extends AbstractCommand<SetSpawnerCommandArguments> implements CompletableCommand {
    @Override
    public void execute(CommandContext context, SetSpawnerCommandArguments args) {
        Player player = (Player) context.sender();
        if (args.getEntityType() == null || !args.getEntityType().isSpawnable()) {
            player.sendMessage("Invalid entity type.");
            return;
        }

        Block targetBlock = player.getTargetBlockExact(5);
        if (targetBlock == null) {
            player.sendMessage("You must look at a block.");
            return;
        }

        if (targetBlock.getType() != Material.SPAWNER) {
            player.sendMessage("You must look at a spawner.");
            return;
        }

        SpawnerTypeChangeEvent event = new SpawnerTypeChangeEvent(player, targetBlock, args.getEntityType());
        event.call();
    }

    @Override
    public TabCompleter complete() {
        return (sender, command, alias, args) -> {
            if (args.length == 1)
                return Arrays.stream(EntityType.values())
                        .filter(EntityType::isSpawnable)
                        .map(Enum::name)
                        .filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
                        .toList();
            return List.of();
        };
    }
}
