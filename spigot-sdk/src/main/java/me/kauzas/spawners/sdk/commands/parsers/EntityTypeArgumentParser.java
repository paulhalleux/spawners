package me.kauzas.spawners.sdk.commands.parsers;

import me.kauzas.spawners.sdk.commands.ArgumentParserInterface;
import org.bukkit.entity.EntityType;

import java.lang.reflect.Field;

/**
 * Argument parser for {@link EntityType}.
 */
public class EntityTypeArgumentParser implements ArgumentParserInterface<EntityType> {
    @Override
    public EntityType parse(String argument, Field ignored) {
        try {
            return EntityType.valueOf(argument.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
