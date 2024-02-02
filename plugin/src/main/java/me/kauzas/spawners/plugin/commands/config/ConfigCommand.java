package me.kauzas.spawners.plugin.commands.config;

import me.kauzas.spawners.sdk.commands.AbstractCompositeCommand;
import me.kauzas.spawners.sdk.commands.CommandContext;
import me.kauzas.spawners.sdk.commands.CommandMeta;

@CommandMeta(name = "config", description = "Config command.", subCommand = true)
public class ConfigCommand extends AbstractCompositeCommand {
    public ConfigCommand() {
        addSubCommand(new ConfigRestoreCommand());
        addSubCommand(new ConfigReloadCommand());
    }

    @Override
    public void main(CommandContext context) {
        showHelp(context);
    }
}
