package me.kauzas.spawners.sdk.commands;

import org.bukkit.command.CommandSender;

/**
 * Represents the context of a command execution.
 */
public record CommandContext(String trigger, CommandSender sender) {
}