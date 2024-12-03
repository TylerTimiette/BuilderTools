package com.buildertools;


import com.buildertools.commands.LightCommand;
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
            ((PluginCommand) Objects.requireNonNull(this.getCommand("lightblock"))).setExecutor(new LightCommand());
        }


    public void onDisable() {
        this.getLogger().info("BuilderTools disbled");
    }

}
