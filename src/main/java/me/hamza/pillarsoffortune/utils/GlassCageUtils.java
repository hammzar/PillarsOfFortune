package me.hamza.pillarsoffortune.utils;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

public class GlassCageUtils {

    public static void createGlassCage(Location center) {
        int[][] directions = {
                {1, 0, 0}, {-1, 0, 0}, {0, 0, 1}, {0, 0, -1}, // Walls
                {1, 1, 0}, {-1, 1, 0}, {0, 1, 1}, {0, 1, -1}, // Upper frame
                {1, -1, 0}, {-1, -1, 0}, {0, -1, 1}, {0, -1, -1}, // Lower frame
                {0, 1, 0}, {0, -1, 0} // Top and bottom center
        };

        for (int[] dir : directions) {
            Location loc = center.clone().add(dir[0], dir[1], dir[2]);
            Block block = loc.getBlock();
            block.setType(Material.GLASS);
        }
    }

    public static void removeGlassCage(Location center) {
        int[][] directions = {
                {1, 0, 0}, {-1, 0, 0}, {0, 0, 1}, {0, 0, -1},
                {1, 1, 0}, {-1, 1, 0}, {0, 1, 1}, {0, 1, -1},
                {1, -1, 0}, {-1, -1, 0}, {0, -1, 1}, {0, -1, -1},
                {0, 1, 0}, {0, -1, 0}
        };

        for (int[] dir : directions) {
            Location loc = center.clone().add(dir[0], dir[1], dir[2]);
            Block block = loc.getBlock();
            if (block.getType() == Material.GLASS) {
                block.setType(Material.AIR);
            }
        }
    }
}
