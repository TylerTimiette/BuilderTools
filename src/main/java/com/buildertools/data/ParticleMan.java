package com.buildertools.data;

import com.buildertools.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class ParticleMan {

    public ParticleMan(){}

    private HashMap<Player, Region> taskList = new HashMap<Player, Region>();
    //I want to make a sort of manager where this plugin starts at the beginning of the server just waiting until someone turns it on.
    public void startTasks() {
        //this task is always repeating, so I can just add them into this queue whenever.
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(Main.getPlugin(), () -> {
            for(Player player : taskList.keySet()) {
                Region region = taskList.get(player);
                //Making sure that they're actually able to see the particles before we commit to sending the info.
                if(player.getWorld().getName().equalsIgnoreCase(region.getWorldname())) {
                    region.spawnRegionParticles(player);
                }
            }
        }, 0, 20);
    }

    //We only support viewing one region at a time.
    public void addTask(Player p, Region r) {
        removeTask(p);
        taskList.put(p, r);
    }

    public void removeTask(Player p) {
        if(taskList.containsKey(p))
            taskList.remove(p);
    }
}
