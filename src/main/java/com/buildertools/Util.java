package com.buildertools;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Util {

    public static boolean checkPlayer(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed as a player.");
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkArgs(CommandSender sender, String[] args, int length, boolean minimum) {
        if (minimum) {
            if (args.length < length) {
                sender.sendMessage("You used an invalid amount of arguments. This command has to have at least " + length + ".");
                return true;
            } else {
                return false;
            }
        } else if (args.length != length) {
            sender.sendMessage("You used an invalid amount of arguments. This command accepts only " + length + ".");
            return true;
        } else {
            return false;
        }
    }
}
