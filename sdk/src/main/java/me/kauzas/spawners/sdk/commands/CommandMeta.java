package me.kauzas.spawners.sdk.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Command meta annotation.
 * Contains all the information about the command.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandMeta {
    /**
     * Name of the command.
     *
     * @return Name of the command.
     */
    String name();

    /**
     * Aliases of the command.
     *
     * @return Aliases of the command.
     */
    String[] aliases() default {};

    /**
     * Description of the command.
     *
     * @return Description of the command.
     */
    String description() default "";

    /**
     * Permission of the command.
     *
     * @return Permission of the command.
     */
    String permission() default "";

    /**
     * Whether the command is only for players.
     *
     * @return Whether the command is only for players.
     */
    boolean playerOnly() default false;

    /**
     * Whether the command is only for console.
     *
     * @return Whether the command is only for console.
     */
    boolean consoleOnly() default false;

    /**
     * Whether the command is a sub command.
     *
     * @return Whether the command is a sub command.
     */
    boolean subCommand() default false;
}
