package com.buildertools;


import com.buildertools.commands.*;
import com.buildertools.data.Database;
import com.buildertools.data.ParticleMan;
import com.buildertools.data.SQLite;
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
    private static final ParticleMan particleMan = new ParticleMan();
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

    public static ParticleMan getParticleMan() { return particleMan; }

    public Database getDatabase() {
        return this.database;
    }

    public void onEnable() {
        this.getLogger().info("BuilderTools enabled");
        if((this.getServer().getPluginManager().getPlugin("PermissionsEx") == null) && (this.getServer().getPluginManager().getPlugin("LuckPerms") == null)) {
            this.getLogger().warning("There's no permission plugin installed! Disabling plugin now\n(You should be using LuckPerms or PEx. If either of these are present, do you have Vault installed?");
            this.getServer().getPluginManager().disablePlugin(this);
        }

        this.getConfig().addDefault("prefix", "&5&lBuilderTools &f&l» &r");
        this.getConfig().addDefault("accent-color", "&d");
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        prefix = this.getConfig().getString("prefix");
        accent = this.getConfig().getString("accent-color");
        this.database = new SQLite(this);
        this.database.load();
        ((PluginCommand) Objects.requireNonNull(this.getCommand("smoothtpset"))).setExecutor(new STPCommand());
        ((PluginCommand) Objects.requireNonNull(this.getCommand("lightblock"))).setExecutor(new LightCommand());
        ((PluginCommand) Objects.requireNonNull(this.getCommand("cmdquery"))).setExecutor(new QueryCMDBlockCommand());
        ((PluginCommand) Objects.requireNonNull(this.getCommand("btreload"))).setExecutor(new ReloadCommand());
        ((PluginCommand) Objects.requireNonNull(this.getCommand("btdebug"))).setExecutor(new DebugCommand());
        this.getServer().getPluginManager().registerEvents(new BlockClickEvent(), this);
        particleMan.startTasks();
        }


    public void onDisable() {
        this.getLogger().info("BuilderTools disabled");
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
