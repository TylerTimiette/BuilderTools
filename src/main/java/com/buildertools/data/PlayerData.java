package com.buildertools.data;

import com.buildertools.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public class PlayerData {
    private final UUID uuid;
    private int x;
    private int y;
    private int z;
    private World world;

    public PlayerData(int x, int y, int z, World world, UUID uuid) {
        this.uuid = uuid;
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public Location getLastCMDBlock() {
        return new Location(world, x, y, z);
    }

    public void setLastCMDBlock(Location location) {
        this.world = location.getWorld();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    public void setX(int i) {
        this.x = i;
    }

    public void setY(int i) {
        this.y = i;
    }

    public void setZ(int i) {
        this.z = i;
    }

    public void setWorld(String s) {
        this.world = Bukkit.getWorld(s);
    }

    public void save() {
        Main.getInstance().getDatabase().setLastCMDBlock(this);
    }
}
