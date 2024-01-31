package me.kauzas.spawners.sdk.commands;

import me.kauzas.spawners.sdk.commands.parsers.DefaultArgumentParser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to define a command argument.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CommandArgument {
    /**
     * The position of the argument.
     * 0-based.
     */
    int position();

    /**
     * The name of the argument.
     */
    String name() default "";

    /**
     * The description of the argument.
     */
    String description() default "";

    /**
     * Whether the argument is required or not.
     */
    boolean required() default true;

    /**
     * The default value of the argument.
     * If the argument is required, this value will be ignored.
     */
    String defaultValue() default "";

    /**
     * Whether all the remaining arguments should be used for this argument.
     */
    boolean untilEnd() default false;

    /**
     * The parser of the argument.
     * If not specified, the default parser will be used and will try to parse the argument to the field type if primitive.
     */
    Class<? extends ArgumentParserInterface<?>> parser() default DefaultArgumentParser.class;
}
