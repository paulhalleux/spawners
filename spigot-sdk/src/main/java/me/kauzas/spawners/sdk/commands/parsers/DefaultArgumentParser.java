package me.kauzas.spawners.sdk.commands.parsers;

import me.kauzas.spawners.sdk.commands.ArgumentParserInterface;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Default argument parser.
 */
public class DefaultArgumentParser implements ArgumentParserInterface<Object> {
    /**
     * Parse the argument to the type.
     *
     * @param argument Argument to parse.
     * @return Parsed argument.
     */
    @Override
    public Object parse(String argument, Field field) {
        Type type = field.getType();
        if (type == String.class) {
            return argument;
        } else if (type == Integer.class || type == int.class) {
            return Integer.parseInt(argument);
        } else if (type == Double.class || type == double.class) {
            return Double.parseDouble(argument);
        } else if (type == Float.class || type == float.class) {
            return Float.parseFloat(argument);
        } else if (type == Long.class || type == long.class) {
            return Long.parseLong(argument);
        } else if (type == Short.class || type == short.class) {
            return Short.parseShort(argument);
        } else if (type == Byte.class || type == byte.class) {
            return Byte.parseByte(argument);
        } else if (type == Boolean.class || type == boolean.class) {
            return Boolean.parseBoolean(argument);
        } else if (type == Character.class || type == char.class) {
            return argument.charAt(0);
        } else {
            return null;
        }
    }
}
