package me.kauzas.spawners.sdk.commands;

import me.kauzas.spawners.sdk.plugin.PluginBase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

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
    public <ArgTypes extends CommandArguments> void handle(AbstractCommand<ArgTypes> abstractCommand, CommandContext context, ArgTypes arguments) {
        if (abstractCommand.canExecute(context, arguments)) {
            abstractCommand.execute(context, arguments);
        }
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        List<AbstractCommand<?>> commands = (List<AbstractCommand<?>>) plugin.getRegisterer(CommandRegisterer.class).getRegisteredItems();
        for (AbstractCommand<?> abstractCommand : commands) {
            if (abstractCommand.getMeta().name().equalsIgnoreCase(command.getName())) {
                CommandArgumentsParser parser = new CommandArgumentsParser(abstractCommand, strings);
                CommandArguments arguments = parser.parse();
                handle((AbstractCommand<? super CommandArguments>) abstractCommand, new CommandContext(s, commandSender, new String[0]), arguments);
            }
        }
        return true;
    }
}
