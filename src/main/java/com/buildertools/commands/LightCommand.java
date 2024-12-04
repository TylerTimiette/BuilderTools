package com.buildertools.commands;

import com.buildertools.Main;
import com.buildertools.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Light;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class LightCommand implements CommandExecutor {

    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (Util.checkPlayer(sender)) {
            return true;
        } else if (Util.checkArgs(sender, args, 1, false)) {
            return true;
        } else {
            Player cs = (Player) sender;
            if(cs.hasPermission("buildertools.light")) {
                try {
                    Integer.parseInt(args[0]);
                    int i = Integer.parseInt(args[0]);
                    if(i >= 0 && i <= 15) {
                        try {
                            Block block = new Location(cs.getWorld(), cs.getLocation().getBlockX(), cs.getLocation().getBlockY(), cs.getLocation().getBlockZ()).getBlock();
                            block.setType(Material.LIGHT);
                            Light light = (Light) block.getBlockData();
                            light.setLevel(i);
                            block.setBlockData(light);
                            cs.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getPrefix() + "Set block at " + Main.getInstance().getAccent() + "x" + block.getX() +",y"+(block.getY())+",z"+block.getZ() + " &rto lightblock with intensity of &7" + i));
                            Main.getInstance().getLogger().info(cs.getName() + " set block at " + block.getLocation().getBlockX()+","+(block.getLocation().getBlockZ())+","+block.getLocation().getBlockZ() + " to lightblock with intensity of " + i + " in world " + block.getWorld().getName());
                        } catch(Exception e) {
                            cs.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getPrefix() + "Exception generated. Check console for more details."));
                            e.printStackTrace();
                        }
                    } else
                        cs.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getPrefix() + "Invalid input! Expected an integer from 0-15, got " + Main.getInstance().getAccent() + args[0] + "&r."));
                } catch (NumberFormatException e) {
                    cs.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getPrefix() + "Invalid input! Expected an integer from 0-15, got " + Main.getInstance().getAccent() + args[0] + "&r."));
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
