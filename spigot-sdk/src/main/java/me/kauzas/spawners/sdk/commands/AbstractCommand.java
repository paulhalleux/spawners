package me.kauzas.spawners.sdk.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommand<ArgTypes extends CommandArguments> {
    /**
     * Get the {@link CommandMeta} annotation of the command.
     *
     * @return CommandMeta of the command or null.
     */
    public CommandMeta getMeta() {
        return this.getClass().getAnnotation(CommandMeta.class);
    }

    /**
     * Execute the command.
     *
     * @param context Command context.
     */
    public abstract void execute(CommandContext context, ArgTypes args);

    /**
     * Method called when a required argument is missing.
     *
     * @param context Command context.
     * @param args    Arguments of the command.
     */
    public void onRequiredArgumentMissing(CommandContext context, ArgTypes args) {
        showHelp(context, args);
    }

    /**
     * Show the help of the command.
     *
     * @param context Command context.
     * @param args    Arguments of the command.
     */
    public void showHelp(CommandContext context, ArgTypes args) {
        List<String> lines = new ArrayList<>();
        CommandMeta meta = getMeta();
        if (meta == null) return;

        String previousArgs = context.previousArgs().length > 0 ? " " + String.join(" ", context.previousArgs()) : "";

        lines.add("§8§m----------------------[§r§7§l HELP §8§m]----------------------");
        
        if (this instanceof AbstractCompositeCommand compositeCommand) {
            for (AbstractCommand<?> command : compositeCommand.getSubCommands()) {
                CommandMeta subMeta = command.getMeta();
                if (subMeta == null) continue;
                lines.add("§6/%s%s §e%s §7- %s".formatted(context.trigger(), previousArgs, subMeta.name(), subMeta.description()));
            }
        } else {
            CommandArgument[] arguments = args.getArgumentList();
            List<String> argumentsList = new ArrayList<>();
            for (CommandArgument argument : arguments) {
                if (argument.required()) {
                    argumentsList.add("§e<%s>".formatted(argument.name()));
                } else {
                    argumentsList.add("§e[%s]".formatted(argument.name()));
                }
            }

            lines.add("§f%s".formatted(meta.description()));
            lines.add("§6/%s%s %s".formatted(context.trigger(), previousArgs, String.join(" ", argumentsList)));
        }

        lines.add("§8§m----------------------------------------------------");
        lines.forEach(context.sender()::sendMessage);
    }

    /**
     * Check if the command can be executed.
     *
     * @param context Command context.
     * @param args    Arguments of the command.
     * @return True if the command can be executed, false otherwise.
     */
    public boolean canExecute(CommandContext context, ArgTypes args) {
        CommandMeta infos = getMeta();

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

        if (!args.hasRequiredArguments()) {
            onRequiredArgumentMissing(context, args);
            return false;
        }

        return true;
    }

    /**
     * Show the help of the command.
     *
     * @param context Command context.
     */
    public void showHelp(CommandContext context) {
        showHelp(context, null);
    }
}