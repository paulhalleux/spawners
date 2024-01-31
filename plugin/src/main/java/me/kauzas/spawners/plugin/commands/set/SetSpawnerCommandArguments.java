package me.kauzas.spawners.plugin.commands.set;

import me.kauzas.spawners.sdk.commands.CommandArgument;
import me.kauzas.spawners.sdk.commands.CommandArguments;
import me.kauzas.spawners.sdk.commands.parsers.EntityTypeArgumentParser;
import org.bukkit.entity.EntityType;

import javax.annotation.Nullable;

/**
 * Arguments of the set command.
 */
public class SetSpawnerCommandArguments extends CommandArguments {
    @CommandArgument(position = 0, name = "entityType", description = "Type of the entity to spawn.", parser = EntityTypeArgumentParser.class)
    private EntityType entityType;

    /**
     * Create a new instance of {@link SetSpawnerCommandArguments}.
     *
     * @param args Raw arguments.
     */
    public SetSpawnerCommandArguments(String[] args) {
        super(args);
    }

    /**
     * Get the type of the entity to spawn.
     *
     * @return Type of the entity to spawn.
     */
    @Nullable
    public EntityType getEntityType() {
        return entityType;
    }
}
