package com.buildertools.commands;

import com.buildertools.Main;
import com.buildertools.Util;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class QueryCMDBlockCommand implements CommandExecutor {

    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (Util.checkPlayer(sender)) {
            return true;
        } else {
            Player cs = (Player) sender;
            Location loc = new Location(cs.getWorld(), cs.getLocation().getBlockX(), cs.getLocation().getBlockY()-1, cs.getLocation().getBlockZ());
            Block block = loc.getBlock();
            if(loc.getBlock().getType() == Material.COMMAND_BLOCK || loc.getBlock().getType() == Material.CHAIN_COMMAND_BLOCK || loc.getBlock().getType() == Material.REPEATING_COMMAND_BLOCK) {
                CommandBlock cmdBlock = (CommandBlock) block.getState();
                if(!cmdBlock.getCommand().equalsIgnoreCase("")) {
                    cs.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getPrefix() + "Command block at " + Main.getInstance().getAccent() + "x" + block.getX() + "," + block.getY() + "," + block.getZ() + " &ris currently set to " + Main.getInstance().getAccent() + cmdBlock.getCommand() + "&r."));
                } else {
                    cs.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getPrefix() + "Command block at " + Main.getInstance().getAccent() + "x" + block.getX() + "," + block.getY() + "," + block.getZ() + " &ris currently" + Main.getInstance().getAccent() + cmdBlock.getCommand() + " unset&r."));

                }
            } else {
                cs.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getPrefix() + "&cERROR: &rCommand block at " + Main.getInstance().getAccent() + "x" + block.getX()+ "," + block.getY() + "," + block.getZ() + "&r is not a command block! Instead it is " + Main.getInstance().getAccent() + block.getType() + "&r."));
            }
        }
        return true;
    }
}
