package me.kauzas.spawners.sdk.commands.parsers;

import me.kauzas.spawners.sdk.commands.ArgumentParserInterface;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

/**
 * Argument parser for {@link Player}.
 */
public class PlayerArgumentParser implements ArgumentParserInterface<Player> {
    @Override
    public Player parse(String argument, Field ignored) {
        return Bukkit.getPlayerExact(argument);
    }
}
