package me.kauzas.spawners.sdk.commands;

import me.kauzas.spawners.sdk.plugin.PluginBase;
import me.kauzas.spawners.sdk.registry.ReflectionRegistry;
import me.kauzas.spawners.sdk.registry.RegisterResult;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Registerer that handles commands.
 */
@SuppressWarnings("rawtypes")
public class CommandRegistry extends ReflectionRegistry<AbstractCommand> {
    private final List<AbstractCommand> commands;
    private final CommandHandlerInterface handler;

    /**
     * Constructor of {@link CommandRegistry}.
     *
     * @param packagePath Path of the package containing the commands.
     * @param plugin      Plugin instance.
     */
    public CommandRegistry(String packagePath, PluginBase plugin, CommandHandlerInterface handler) {
        super(packagePath, plugin);
        this.commands = new ArrayList<>();
        this.handler = handler;
    }

    /**
     * Constructor of {@link CommandRegistry}.
     * Creates a {@link DefaultCommandHandler} as handler.
     *
     * @param packagePath Path of the package containing the commands.
     * @param plugin      Plugin instance.
     */
    public CommandRegistry(String packagePath, PluginBase plugin) {
        this(packagePath, plugin, new DefaultCommandHandler(plugin));
    }

    @Override
    public RegisterResult register(AbstractCommand object) {
        CommandMeta meta = object.getMeta();
        if (meta == null) return RegisterResult.FAILED;
        PluginCommand command = getPlugin().getCommand(meta.name());

        if (command == null) {
            getPlugin().getLogger().warning("Failed to register command " + object.getClass().getSimpleName() + " : Make sure the name in the @CommandMeta annotation is the same as the name in the plugin.yml.");
            return RegisterResult.FAILED;
        }

        // If the command implements CommandExecutor, set it as executor
        command.setExecutor(handler);
        command.setDescription(meta.description());
        command.setAliases(List.of(meta.aliases()));

        // If the command implements CompletableCommand, set it as tab completer
        if (object instanceof CompletableCommand) {
            command.setTabCompleter(((CompletableCommand) object).complete());
        }

        // If the command implements CommandExecutor, override the executor with it
        if (object instanceof CommandExecutor) {
            command.setExecutor((CommandExecutor) object);
        }

        commands.add(object);
        return RegisterResult.SUCCESS;
    }

    @Override
    public List<AbstractCommand> getRegisteredItems() {
        return commands;
    }

    @Override
    public boolean skip(Class<? extends AbstractCommand> object) {
        CommandMeta meta = object.getAnnotation(CommandMeta.class);
        return meta == null || meta.subCommand();
    }
}
