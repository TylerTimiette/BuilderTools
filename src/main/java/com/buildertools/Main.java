package com.buildertools;


import com.buildertools.commands.LightCommand;
import com.buildertools.commands.QueryCMDBlockCommand;
import com.buildertools.commands.ReloadCommand;
import com.buildertools.commands.STPCommand;
import com.buildertools.data.Database;
import com.buildertools.listeners.BlockClickEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin  {
    private static Main instance;
    private Database database;
    private String prefix;
    private String accent;
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
        this.getConfig().addDefault("accent-color", "&d");
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        prefix = this.getConfig().getString("prefix");
        accent = this.getConfig().getString("accent-color");
        ((PluginCommand) Objects.requireNonNull(this.getCommand("smoothtpset"))).setExecutor(new STPCommand());
        ((PluginCommand) Objects.requireNonNull(this.getCommand("lightblock"))).setExecutor(new LightCommand());
        ((PluginCommand) Objects.requireNonNull(this.getCommand("cmdquery"))).setExecutor(new QueryCMDBlockCommand());
        ((PluginCommand) Objects.requireNonNull(this.getCommand("btreload"))).setExecutor(new ReloadCommand());
        this.getServer().getPluginManager().registerEvents(new BlockClickEvent(), this);
        }


    public void onDisable() {
        this.getLogger().info("BuilderTools disbled");
    }

    public void reloadConfig(){
        super.reloadConfig();
        prefix = this.getConfig().getString("prefix");
        accent = this.getConfig().getString("accent-color");
    }

    public String getPrefix() {
        return prefix;
    }

    public String getAccent() {
        return accent;
    }


}
