package me.kauzas.spawners.sdk.commands;

/**
 * Represents an argument parser.
 *
 * @param <Out> Type of the parsed argument.
 */
public interface ArgumentParserInterface<Out> {
    /**
     * Parse the argument.
     *
     * @param argument Argument to parse.
     * @return Parsed argument.
     */
    Out parse(String argument);
}
