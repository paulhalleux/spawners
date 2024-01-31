package me.kauzas.spawners.sdk.commands;

import me.kauzas.spawners.sdk.plugin.PluginBase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Default command handler.
 * Will be registered as a {@link org.bukkit.command.CommandExecutor} in the plugin.
 */
public class DefaultCommandHandler implements CommandHandlerInterface {
    /**
     * Plugin instance.
     */
    private final PluginBase plugin;

    /**
     * Constructor of {@link DefaultCommandHandler}.
     *
     * @param plugin Plugin instance.
     */
    public DefaultCommandHandler(PluginBase plugin) {
        this.plugin = plugin;
    }

    @Override
    public <ArgTypes extends CommandArguments> boolean handle(AbstractCommand<ArgTypes> abstractCommand, CommandContext context, ArgTypes arguments) {
        CommandMeta infos = abstractCommand.getMeta();

        if (infos.playerOnly() && !(context.sender() instanceof Player)) {
            context.sender().sendMessage("This command can only be executed by a player.");
            return false;
        }

        if (infos.consoleOnly() && !(context.sender() instanceof ConsoleCommandSender)) {
            context.sender().sendMessage("This command can only be executed by the console.");
            return false;
        }

        boolean hasPermission = infos.permission().isEmpty() || context.sender().hasPermission(infos.permission());
        if (!hasPermission) {
            context.sender().sendMessage("You don't have the permission to execute this command.");
            return false;
        }

        abstractCommand.execute(context, arguments);
        return false;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        List<AbstractCommand<?>> commands = (List<AbstractCommand<?>>) plugin.getRegisterer(CommandRegisterer.class).getRegisteredItems();
        for (AbstractCommand<?> abstractCommand : commands) {
            if (abstractCommand.getMeta().name().equalsIgnoreCase(command.getName())) {
                CommandArgumentsParser parser = new CommandArgumentsParser(abstractCommand, strings);
                CommandArguments arguments = parser.parse();
                return handle((AbstractCommand<? super CommandArguments>) abstractCommand, new CommandContext(s, commandSender), arguments);
            }
        }
        return false;
    }
}
