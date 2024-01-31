package me.kauzas.spawners.sdk.commands;

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
}