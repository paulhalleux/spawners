package me.kauzas.spawners.sdk.commands;

import me.kauzas.spawners.sdk.registry.SkipRegistration;
import org.bukkit.command.TabCompleter;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract class representing a composite command.
 * A composite command is a command that can have sub commands.
 * The sub commands are registered in the constructor of the class with the method {@link #command(AbstractCommand)}.
 * <br/><br/>
 * The first argument of the command will be used to determine which sub command to execute.
 * If the command is executed without any argument, the method {@link #main(CommandContext)} will be called.
 */
@SkipRegistration
public abstract class AbstractCompositeCommand extends AbstractCommand<CommandArguments.None> implements CompletableCommand {
    /**
     * List of sub commands.
     */
    private final List<AbstractCommand<?>> subCommands;

    /**
     * Constructor of {@link AbstractCompositeCommand}.
     */
    protected AbstractCompositeCommand() {
        this.subCommands = new ArrayList<>();
    }

    /**
     * Add a sub command to the command.
     * The name of the sub command will be matched to the first argument of the command.
     *
     * @param command Sub command to add.
     */
    public void command(AbstractCommand<?> command) {
        subCommands.add(command);
    }

    /**
     * Main method of the command. This method is called when the command is executed without any argument.
     *
     * @param context Command context.
     */
    public abstract void main(CommandContext context);

    /**
     * Method called when the sub command is unknown.
     *
     * @param context        Command context.
     * @param subCommandName Name of the unknown sub command.
     */
    public void unknown(CommandContext context, String subCommandName) {
        context.sender().sendMessage("§cUnknown sub command: §7" + subCommandName);
    }

    /**
     * Get the list of sub commands.
     *
     * @return List of sub commands.
     */
    public List<AbstractCommand<?>> getSubCommands() {
        return subCommands;
    }

    /**
     * Execute the command with the given arguments and determine which sub command to execute.
     *
     * @param context Command context.
     * @param args    Command arguments.
     */
    @Override
    public void execute(CommandContext context, CommandArguments.None args, Session session) {
        if (args == null || args.getRawArgs().length == 0) {
            main(context);
            return;
        }

        String subCommandName = args.getRawArgs()[0].toLowerCase();
        for (AbstractCommand<?> subCommand : subCommands) {
            if (!subCommand.getMeta().name().equalsIgnoreCase(subCommandName)) {
                continue;
            }

            String[] subCommandArgs = Arrays.copyOfRange(args.getRawArgs(), 1, args.getRawArgs().length);
            CommandArgumentsParser parser = new CommandArgumentsParser(subCommand, subCommandArgs);

            // We need to add the sub command name to the previous arguments.
            String[] previousArgs = Arrays.copyOf(context.previousArgs(), context.previousArgs().length + 1);
            previousArgs[previousArgs.length - 1] = subCommandName;

            CommandContext newContext = new CommandContext(
                    context.trigger(),
                    context.sender(),
                    previousArgs
            );

            if (subCommand.canExecute(newContext, parser.parse())) {
                subCommand.execute(newContext, parser.parse(), session);
            }

            return;
        }

        unknown(context, subCommandName);
    }

    @Override
    public TabCompleter complete() {
        return (sender, command, alias, args) -> {
            // If there is only one argument, we need to complete the sub command name.
            if (args.length == 1) {
                return subCommands.stream()
                        .map(AbstractCommand::getMeta)
                        .map(CommandMeta::name)
                        .filter(name -> name.startsWith(args[0]))
                        .toList();
            }

            // If there is more than one argument, we need to find the sub command
            // and complete its arguments.
            if (args.length > 1) {
                String subCommandName = args[0].toLowerCase();
                for (AbstractCommand<?> subCommand : subCommands) {
                    if (!subCommand.getMeta().name().equalsIgnoreCase(subCommandName) || !(subCommand instanceof CompletableCommand completableCommand)) {
                        continue;
                    }

                    String[] subCommandArgs = Arrays.copyOfRange(args, 1, args.length);
                    return completableCommand.complete().onTabComplete(sender, command, alias, subCommandArgs);
                }
            }

            // If the sub command is unknown, we return an empty list.
            return List.of();
        };
    }
}
