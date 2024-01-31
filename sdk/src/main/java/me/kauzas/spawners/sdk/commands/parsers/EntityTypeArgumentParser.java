package me.kauzas.spawners.sdk.commands.parsers;

import me.kauzas.spawners.sdk.commands.ArgumentParserInterface;
import org.bukkit.entity.EntityType;

/**
 * Argument parser for {@link EntityType}.
 */
public class EntityTypeArgumentParser implements ArgumentParserInterface<EntityType> {
    @Override
    public EntityType parse(String argument) {
        try {
            return EntityType.valueOf(argument.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
