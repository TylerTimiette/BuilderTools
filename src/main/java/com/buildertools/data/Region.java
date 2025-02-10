package com.buildertools.data;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Region {
    HashMap<Location, Particle> particleMap = new HashMap<Location, Particle>();
    String worldname;
    //Ideally we only want to call this when we know that we need to load this region, which is why I'm okay with doing this.
    //Additionally, the particle is also tied to a block already because of the location.
    public Region(Location[] locations, Particle[] particles) {
    for(int i = 0; i < locations.length; i++) {
        particleMap.put(locations[i], particles[i]);
    }
    worldname = locations[0].getWorld().getName();
    //They're always going to be in the same world as each other from Location to Location, since they are stored together.
    }

    public String getWorldname(){ return worldname; }

    //Display what we've found
    public void spawnRegionParticles(Player player) {
        for(Location location : particleMap.keySet()) {
            player.spawnParticle(particleMap.get(location), location, 1);
        }
    }

}
