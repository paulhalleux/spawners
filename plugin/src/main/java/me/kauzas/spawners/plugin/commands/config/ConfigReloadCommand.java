package me.kauzas.spawners.plugin.commands.config;

import me.kauzas.spawners.plugin.Locale;
import me.kauzas.spawners.plugin.Main;
import me.kauzas.spawners.sdk.commands.AbstractCommand;
import me.kauzas.spawners.sdk.commands.CommandArguments;
import me.kauzas.spawners.sdk.commands.CommandContext;
import me.kauzas.spawners.sdk.commands.CommandMeta;
import me.kauzas.spawners.sdk.configuration.PluginFile;

@CommandMeta(name = "reload", description = "Reload the configuration files.", subCommand = true)
public class ConfigReloadCommand extends AbstractCommand<CommandArguments.None> {
    @Override
    public void execute(CommandContext context, CommandArguments.None args) {
        Main.getInstance().getFiles().forEach(PluginFile::reload);
        context.sender().sendMessage(Locale.prefixed("config-reloaded"));
    }
}
