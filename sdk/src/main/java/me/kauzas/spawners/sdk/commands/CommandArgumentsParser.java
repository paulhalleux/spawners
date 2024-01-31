package me.kauzas.spawners.sdk.commands;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Class that parse command arguments.
 */
public class CommandArgumentsParser {
    /**
     * Command to parse arguments for.
     */
    private final AbstractCommand<? extends CommandArguments> command;

    /**
     * Arguments to parse.
     */
    private final String[] args;

    public CommandArgumentsParser(AbstractCommand<? extends CommandArguments> command, String[] args) {
        this.command = command;
        this.args = args;
    }

    /**
     * Parse arguments.
     *
     * @return Parsed arguments.
     */
    public <R extends CommandArguments> R parse() {
        Type type = getArgumentsType();
        if (type == null && command instanceof AbstractCompositeCommand) return (R) new CommandArguments.None(args);
        if (type == null) return null;
        if (type == CommandArguments.None.class) return (R) new CommandArguments.None(args);

        Map<Field, Object> fieldValueMap = getArgumentFieldsAndValues(type);
        return (R) createInstance(type, fieldValueMap);
    }

    /**
     * Get the generic type of {@link AbstractCommand}.
     *
     * @return Type of the arguments.
     */
    private Type getArgumentsType() {
        Type superclass = command.getClass().getGenericSuperclass();
        if (superclass instanceof Class) return null;
        return ((java.lang.reflect.ParameterizedType) superclass).getActualTypeArguments()[0];
    }

    /**
     * Get all fields in type with {@link CommandArgument} annotation.
     *
     * @return Fields with {@link CommandArgument} annotation.
     */
    private List<Field> getArgumentFields(Type type) {
        List<Field> fields = new ArrayList<>(List.of(((Class<?>) type).getDeclaredFields()));
        fields.removeIf(field -> !field.isAnnotationPresent(CommandArgument.class));
        return fields;
    }

    /**
     * Get all fields in type with {@link CommandArgument} annotation and their values.
     *
     * @return Fields with {@link CommandArgument} annotation and their values.
     */
    private Map<Field, Object> getArgumentFieldsAndValues(Type type) {
        List<Field> fields = getArgumentFields(type);
        Map<Field, Object> fieldValueMap = new LinkedHashMap<>();

        fields.forEach(field -> {
            CommandArgument annotation = field.getAnnotation(CommandArgument.class);
            int index = annotation.position();
            if (index >= args.length) return;
            String atIndex = args[index] == null ? annotation.required() ? null : annotation.defaultValue() : args[index];
            String value = annotation.untilEnd() ? String.join(" ", Arrays.copyOfRange(args, index, args.length)) : atIndex;

            // Might require a better solution
            try {
                ArgumentParserInterface<?> parser = annotation.parser().getDeclaredConstructor(Type.class).newInstance(field.getType());
                Object parsedValue = parser.parse(value);
                fieldValueMap.put(field, parsedValue);
            } catch (Exception ignored) {
                try {
                    ArgumentParserInterface<?> parser = annotation.parser().getDeclaredConstructor().newInstance();
                    Object parsedValue = parser.parse(value);
                    fieldValueMap.put(field, parsedValue);
                } catch (Exception e) {
                    System.err.println("Cannot parse argument " + value + " for field " + field.getName() + " in " + type.getTypeName());
                }
            }
        });

        return fieldValueMap;
    }

    /**
     * Create an instance of the arguments.
     *
     * @param type          Type of the arguments.
     * @param fieldValueMap Map of field name and value.
     * @return Instance of the arguments.
     */
    private CommandArguments createInstance(Type type, Map<Field, Object> fieldValueMap) {
        try {
            CommandArguments arguments = (CommandArguments) ((Class<?>) type)
                    .getDeclaredConstructor(String[].class)
                    .newInstance((Object) args);

            fieldValueMap.forEach((field, value) -> {
                try {
                    field.setAccessible(true);
                    field.set(arguments, value);
                } catch (IllegalAccessException e) {
                    System.err.println("Cannot set field " + field.getName() + " in " + arguments.getClass().getSimpleName() + " with value " + value);
                }
            });

            return arguments;
        } catch (Exception e) {
            System.err.println("Cannot create instance of " + type.getTypeName() + " with arguments " + Arrays.toString(args));
        }
        return null;
    }
}
