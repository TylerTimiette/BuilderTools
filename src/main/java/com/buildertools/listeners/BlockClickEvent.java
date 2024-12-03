package com.buildertools.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.data.type.Light;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

//Cred for this goes to DNAmaster10, because I wouldn't know how to do this otherwise. This is for light-blocks.
public class BlockClickEvent implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onBlockClick(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(e.getClickedBlock() != null) {
                if(e.getClickedBlock().getState().getType() == Material.LIGHT) {
                    if(e.getPlayer().hasPermission("buildertools.light")) {
                        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
                            Light light = (Light) e.getClickedBlock().getBlockData();
                            int lightLevel = light.getLevel();
                            int maxLevel = light.getMaximumLevel();
                            int newLevel;

                            if (lightLevel == maxLevel)
                                newLevel = 0;
                            else
                                newLevel = lightLevel + 1;

                            light.setLevel(newLevel);
                            e.getClickedBlock().setBlockData(light);

                        }
                    }
                }
            }
        }
    }

}
