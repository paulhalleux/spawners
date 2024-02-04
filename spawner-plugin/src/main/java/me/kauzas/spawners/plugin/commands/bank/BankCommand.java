package me.kauzas.spawners.plugin.commands.bank;

import me.kauzas.spawners.sdk.commands.AbstractCommand;
import me.kauzas.spawners.sdk.commands.CommandArguments;
import me.kauzas.spawners.sdk.commands.CommandContext;
import me.kauzas.spawners.sdk.commands.CommandMeta;
import org.hibernate.Session;

import javax.annotation.Nullable;

@CommandMeta(name = "bank", description = "Bank command.", subCommand = true)
public class BankCommand extends AbstractCommand<CommandArguments.None> {
    @Override
    public void execute(CommandContext context, CommandArguments.None args, @Nullable Session session) {
    }
}
