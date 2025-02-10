package com.buildertools.commands;

import com.buildertools.Main;
import com.buildertools.Util;
import com.buildertools.data.Region;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Light;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class DebugCommand implements CommandExecutor {
    String failureMessage = Main.getInstance().getConfig().getString("prefix") + "This command must be run by an operator.";
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
    if (Util.checkPlayer(sender)) {
        return true;
    }

    Player cs = (Player) sender;
        if(cs.isOp()) {
            cs.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("prefix") + "Starting count of regions in your current world."));
            Main.getInstance().getDatabase().numberOfRegions(cs.getWorld());
            } else
                cs.sendMessage(ChatColor.translateAlternateColorCodes('&', failureMessage));
        return true;
    }

}
