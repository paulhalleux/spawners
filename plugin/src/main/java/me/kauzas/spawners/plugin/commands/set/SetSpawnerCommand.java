package me.kauzas.spawners.plugin.commands.set;

import me.kauzas.spawners.plugin.Locale;
import me.kauzas.spawners.plugin.events.SpawnerTypeChangeEvent;
import me.kauzas.spawners.sdk.commands.AbstractCommand;
import me.kauzas.spawners.sdk.commands.CommandContext;
import me.kauzas.spawners.sdk.commands.CommandMeta;
import me.kauzas.spawners.sdk.commands.CompletableCommand;
import me.kauzas.spawners.sdk.events.DomainEvent;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@CommandMeta(name = "set", description = "Set a spawner type.", subCommand = true, playerOnly = true)
public class SetSpawnerCommand extends AbstractCommand<SetSpawnerCommandArguments> implements CompletableCommand {
    @Override
    public void execute(CommandContext context, SetSpawnerCommandArguments args) {
        Player player = (Player) context.sender();
        if (args.getEntityType() == null || !args.getEntityType().isSpawnable()) {
            String entity = args.getEntityType() == null ? args.getRawArg(0, "") : args.getEntityType().name();
            player.sendMessage(Locale.prefixed("error.invalid-entity", Map.of("entity", entity)));
            return;
        }

        Block targetBlock = player.getTargetBlockExact(5);
        if (targetBlock == null) {
            player.sendMessage(Locale.prefixed("error.no-target-block"));
            return;
        }

        if (targetBlock.getState() instanceof CreatureSpawner spawner) {
            SpawnerTypeChangeEvent event = new SpawnerTypeChangeEvent(player, spawner, args.getEntityType());
            DomainEvent.call(event);
            return;
        }

        player.sendMessage(Locale.prefixed("error.invalid-block"));
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
