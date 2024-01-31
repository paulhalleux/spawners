package me.kauzas.spawners.sdk.commands;

import org.bukkit.command.TabCompleter;

/**
 * Interface representing a completable command.
 * A completable command is a command that can be tab completed.
 */
public interface CompletableCommand {
    /**
     * Get the tab completer of the command.
     *
     * @return Tab completer of the command.
     */
    TabCompleter complete();
}
