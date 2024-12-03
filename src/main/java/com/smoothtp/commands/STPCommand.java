package com.smoothtp.commands;

import com.smoothtp.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class STPCommand implements CommandExecutor {

    String failureMessage = Main.getInstance().getConfig().getString("prefix") + "Incorrect usage! This command takes 9 total arguments and most be used standing on a command block;\n" +
            "&fArg #1 &7(X) &f-- &2&oX &aposition of teleport range\n" +
            "&fArg #2 &7(Y) &f-- &2&oY &aposition of teleport range\n" +
            "&fArg #3 &7(Z) &f-- &2&oZ &aposition of teleport range\n\n" +
            "&fArg #4 &7(DX) &f-- &aHow far the detection range extends in the &2&oX &aaxis\n" +
            "&fArg #5 &7(DY) &f-- &aHow far the detection range extends in the &2&oY &aaxis\n" +
            "&fArg #6 &7(DZ) &f-- &aHow far the detection range extends in the &2&oZ &aaxis\n\n" +
            "&fArg #7 &7(X2) &f-- &aHow far to teleport the player in the &2&oX&a axis as an offset from their current position once they step into range\n" +
            "&fArg #8 &7(Y2) &f-- &aHow far to teleport the player in the &2&oY&a axis as an offset from their current position once they step into range\n" +
            "&fArg #9 &7(Y2) &f-- &aHow far to teleport the player in the &2&oZ&a axis as an offset from their current position once they step into range\n&fTildes &7(~) &fare added automatically to the final 3 arguments.";

    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (Main.checkPlayer(sender)) {
            return true;
        } else if(Main.checkArgs(sender, args, 9, false)) {


            for(int i = 0; i < args.length; i++) {
                try {
                    Double.parseDouble(args[i]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lERROR&0: &cArgument &4#" + i+1 + " &cis invalid! We are expecting a double for each argument.\n" + failureMessage));
                    return true;
                }
            }
            //I think we assume that it worked if we got to this chunk of code, no?
            Player cs = (Player) sender;
            //needs to be run on a command block
            if(cs.getLocation().getBlock().getType() == Material.COMMAND_BLOCK || cs.getLocation().getBlock().getType() == Material.REPEATING_COMMAND_BLOCK || cs.getLocation().getBlock().getType() == Material.CHAIN_COMMAND_BLOCK) {
                CommandBlock cmdBlock = (CommandBlock) cs.getLocation().getBlock();
                //minecraft:execute as @n[x=1496,y=28,z=38,dx=-0.4,dy=2,dz=-1] at @s run minecraft:tp @s ~-2.5 ~30 ~
                cmdBlock.setCommand("minecraft:execute as @n[x=" + args[0] + ",y=" + args[1] +",z=" + args[2] +",dx=" + args[3] + ",dy=" + args[4] + ",dz=" + args[5] + " at @s run minecraft:tp @s ~" + args[6] + " ~" + args[7] + " ~" + args[8]);
                Main.getInstance().getLogger().info(cs.getName() + " set command block at " + cs.getLocation() + " to " + args[0] + ", " + args[1] + ", " + args[2] + ", " + args[3] + ", " + args[4] + ", " + args[5] + ", ~" + args[6] + ", ~" + args[7] + ", ~" + args[8]);
            }

        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', failureMessage));
        }
        return true;
    }
}