package me.kauzas.spawners.plugin.commands.config;

import me.kauzas.spawners.plugin.Locale;
import me.kauzas.spawners.plugin.Main;
import me.kauzas.spawners.sdk.commands.*;
import me.kauzas.spawners.sdk.configuration.PluginFile;
import org.bukkit.command.TabCompleter;
import org.hibernate.Session;

import javax.annotation.Nullable;
import java.util.Map;

@CommandMeta(name = "dump", description = "Dump the configuration content in the chat.", subCommand = true)
public class ConfigDumpCommand extends AbstractCommand<ConfigDumpCommand.Arguments> implements CompletableCommand {
    @Override
    public void execute(CommandContext context, ConfigDumpCommand.Arguments args, @Nullable Session session) {
        PluginFile file = Main.getInstance().getFile(args.getFileName());
        if (file == null) {
            context.sender().sendMessage(Locale.prefixed("error.file-not-found"));
            return;
        }

        context.sender().sendMessage(Locale.prefixed("config.file-header", Map.of("filename", args.getFileName())));
        file.getFlatMap().forEach((key, value) -> {
            context.sender().sendMessage(Locale.get("config.dump-format", Map.of("key", key, "value", value)));
        });
    }

    @Override
    public TabCompleter complete() {
        return (sender, command, alias, args) -> {
            if (args.length == 1)
                return Main.getInstance().getFilesMap()
                        .keySet()
                        .stream()
                        .filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
                        .toList();
            return null;
        };
    }

    public static class Arguments extends CommandArguments {
        @CommandArgument(name = "file-name", position = 0, description = "The file to dump.")
        private String fileName;

        public Arguments(String[] args) {
            super(args);
        }

        /**
         * Get the file name to dump.
         *
         * @return File name.
         */
        public String getFileName() {
            return fileName;
        }
    }
}