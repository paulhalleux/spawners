package me.kauzas.spawners.sdk.commands;

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
    public String getRawArg(int index) {
        if (index >= args.length) {
            return null;
        }
        return args[index];
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
