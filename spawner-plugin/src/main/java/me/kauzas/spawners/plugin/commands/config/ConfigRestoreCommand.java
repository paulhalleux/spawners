package me.kauzas.spawners.plugin.commands.config;

import me.kauzas.spawners.plugin.Locale;
import me.kauzas.spawners.plugin.Main;
import me.kauzas.spawners.sdk.commands.*;
import me.kauzas.spawners.sdk.configuration.PluginFile;
import org.hibernate.Session;

import javax.annotation.Nullable;

@CommandMeta(name = "restore", description = "Restore the configuration files to default.", subCommand = true)
public class ConfigRestoreCommand extends AbstractCommand<CommandArguments.None> {
    @Override
    public void execute(CommandContext context, CommandArguments.None args, @Nullable Session session) {
        Main.getInstance().getFiles().forEach(PluginFile::restoreDefaultFile);
        context.sender().sendMessage(Locale.prefixed("config.restored"));
    }
}
