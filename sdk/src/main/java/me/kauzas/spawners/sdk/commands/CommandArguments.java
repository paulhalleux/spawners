package me.kauzas.spawners.sdk.commands;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Represents the arguments of a command.
 */
public abstract class CommandArguments {
    private final String[] args;

    /**
     * Create a new instance of CommandArguments.
     *
     * @param args Raw arguments.
     */
    public CommandArguments(final String[] args) {
        this.args = args;
    }

    /**
     * Get the raw arguments.
     *
     * @return Raw arguments.
     */
    public String[] getRawArgs() {
        return args;
    }

    /**
     * Get the argument at the specified index.
     *
     * @param index Index of the argument.
     * @return Argument at the specified index or null if the index is out of bounds.
     */
    @Nullable
    public String getRawArg(int index) {
        return getRawArg(index, null);
    }

    /**
     * Get the argument at the specified index.
     *
     * @param index Index of the argument.
     * @param def   Default value if the index is out of bounds.
     * @return Argument at the specified index or the default value if the index is out of bounds.
     */
    public String getRawArg(int index, String def) {
        if (index >= args.length) {
            return def;
        }
        String arg = args[index];
        return arg == null ? def : arg;
    }

    /**
     * Check if the argument at the specified index is present.
     *
     * @param index Index of the argument.
     * @return True if the argument is present, false otherwise.
     */
    public boolean isArgumentSet(int index) {
        return index < args.length;
    }

    /**
     * Get the list of arguments.
     *
     * @return Array of arguments.
     */
    public CommandArgument[] getArgumentList() {
        Field[] fields = this.getClass().getDeclaredFields();
        return Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(CommandArgument.class))
                .map(field -> field.getAnnotation(CommandArgument.class))
                .toArray(CommandArgument[]::new);
    }

    /**
     * Check if the command has all required arguments.
     *
     * @return True if all required arguments are present, false otherwise.
     */
    public boolean hasRequiredArguments() {
        for (CommandArgument commandArgument : getArgumentList()) {
            if (commandArgument.required() && !isArgumentSet(commandArgument.position())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Empty arguments.
     */
    public static class None extends CommandArguments {
        public None(String[] args) {
            super(args);
        }
    }
}
