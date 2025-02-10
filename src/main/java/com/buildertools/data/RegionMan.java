package com.buildertools.data;

import com.buildertools.Main;
import org.bukkit.Location;

public class RegionMan {
//'world' varchar NOT NULL, 'number' int NOT NULL, 'size' NOT NULL, 'x-boundary', int NOT NULL 'y-boundary', 'z-boundary' int NOT NULL, 'start-x' int NOT NULL, 'start-y' int NOT NULL, 'start-z' int NOTNULL, 'note' varchar NOT NULL, PRIMARY KEY('world'))
    public static void createRegion(String world, int size, int xboundary, int yboundary, int zboundary, Location start) {

        Main.getInstance().getDatabase().setRegion(world, size, xboundary, yboundary, zboundary, start);
        saveRegions();
    }

    public static void saveRegions() {

    }
}
