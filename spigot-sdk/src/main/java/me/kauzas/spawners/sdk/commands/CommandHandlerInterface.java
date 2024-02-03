package me.kauzas.spawners.sdk.commands;

import org.bukkit.command.CommandExecutor;

/**
 * Interface that represents a command handler.
 * Extends {@link CommandExecutor} to be able to register the command in the plugin.
 */
public interface CommandHandlerInterface extends CommandExecutor {
    /**
     * Method that handle command execution.
     *
     * @param abstractCommand Executed command.
     * @param context         Command context.
     * @param arguments       Command arguments.
     */
    <ArgTypes extends CommandArguments> void handle(AbstractCommand<ArgTypes> abstractCommand, CommandContext context, ArgTypes arguments);
}
