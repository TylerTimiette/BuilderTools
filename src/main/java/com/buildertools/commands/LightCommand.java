package com.buildertools.commands;

import com.buildertools.Main;
import com.buildertools.Util;
import org.bukkit.Bukkit;
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

public class LightCommand implements CommandExecutor {

    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (Util.checkPlayer(sender)) {
            return true;
        } else if (Util.checkArgs(sender, args, 1, false)) {
            Player cs = (Player) sender;
            if(cs.hasPermission("buildertools.light")) {
                try {
                    Integer.parseInt(args[0]);
                    int i = Integer.parseInt(args[0]);
                    if(i >= 0 && i <= 15) {
                        try {
                            Block block = new Location(cs.getWorld(), cs.getLocation().getBlockX(), cs.getLocation().getBlockY() + 1, cs.getLocation().getBlockZ()).getBlock();
                            Light light = (Light) block.getBlockData();
                            light.setLevel(i);
                            block.setType(Material.LIGHT);
                            block.setBlockData(light);
                            cs.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("prefix") + "Set block at " + block.getLocation().getBlockX()+","+(block.getLocation().getBlockZ()+1)+","+block.getLocation().getBlockZ() + " to lightblock with intensity of " + i));
                            Main.getInstance().getLogger().info(cs.getName() + " set block at " + block.getLocation().getBlockX()+","+(block.getLocation().getBlockZ()+1)+","+block.getLocation().getBlockZ() + " to lightblock with intensity of " + i + " in world " + block.getWorld().getName());
                        } catch(Exception e) {
                            cs.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("prefix") + "Exception generated. Check console for more details."));
                            e.printStackTrace();
                        }
                    } else
                        cs.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("prefix")) + "Invalid input! Expected an integer from 0-15, got &7" + args[0] + "&r.");
                } catch (NumberFormatException e) {
                    cs.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("prefix")) + "Invalid input! Expected an integer from 0-15, got &7" + args[0] + "&r.");
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
