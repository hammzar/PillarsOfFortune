package me.hamza.pillarsoffortune.utils;

import org.bukkit.Location;

import java.util.Objects;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

public class ObjectSerializer {

    public static String serializeLocation(Location location) {
        return Objects.requireNonNull(location.getWorld()).getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
    }

    public static Location deserializeLocation(String str) {
        String[] parts = str.split(",");
        if (parts.length != 6) {
            return null;
        }

        org.bukkit.World world = org.bukkit.Bukkit.getWorld(parts[0]);
        if (world == null) {
            return null;
        }

        return new Location(
                world,
                Double.parseDouble(parts[1]),
                Double.parseDouble(parts[2]),
                Double.parseDouble(parts[3]),
                Float.parseFloat(parts[4]),
                Float.parseFloat(parts[5])
        );
    }

}
