package com.buildertools.commands;


import com.buildertools.Main;
import com.buildertools.Util;
import io.papermc.paper.event.player.AsyncChatCommandDecorateEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import javax.annotation.Nonnull;

public class ReloadCommand implements CommandExecutor {

    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            Main.getInstance().reloadConfig();
            sender.sendMessage("Reloaded config.");
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getPrefix()) + "This command may only be run by console.");
            return true;
        }
        return true;
    }
}
