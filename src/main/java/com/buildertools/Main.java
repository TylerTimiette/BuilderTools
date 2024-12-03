package com.buildertools;


import com.buildertools.commands.STPCommand;
import com.buildertools.data.Database;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin  {
    private static Main instance;
    private Database database;
    //private WhoAmICommand whois = new WhoAmICommand();

    public Main() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }

    public static Plugin getPlugin() {
        return getPlugin(Main.class);
    }


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

    public Database getDatabase() {
        return this.database;
    }

    public void onEnable() {
        this.getLogger().info("BuilderTools enabled");
        if((this.getServer().getPluginManager().getPlugin("PermissionsEx") == null) && (this.getServer().getPluginManager().getPlugin("LuckPerms") == null)) {
            this.getLogger().warning("There's no permission plugin installed! Disabling plugin now\n(You should be using LuckPerms or PEx. If either of these are present, do you have Vault installed?");
        }

            this.getConfig().addDefault("prefix", "&5&lBuilderTools &f&l» &r");
            this.getConfig().options().copyDefaults(true);
            this.saveConfig();
            ((PluginCommand) Objects.requireNonNull(this.getCommand("smoothtpset"))).setExecutor(new STPCommand());

        }


    public void onDisable() {
        this.getLogger().info("BuilderTools disbled");
    }

}
