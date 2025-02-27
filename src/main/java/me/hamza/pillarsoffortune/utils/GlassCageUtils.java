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
                {1, 0, 0}, {-1, 0, 0},  // Left & Right Walls (Body Level)
                {0, 0, 1}, {0, 0, -1},  // Front & Back Walls (Body Level)
                {1, 1, 0}, {-1, 1, 0}, {0, 1, 1}, {0, 1, -1},  // Walls at Head Level
                {1, 2, 0}, {-1, 2, 0}, {0, 2, 1}, {0, 2, -1},  // Walls at Top Level
                {0, 2, 0}, // Top Cover (Directly Above Player's Head)
                {1, -1, 0}, {-1, -1, 0}, {0, -1, 1}, {0, -1, -1}, // Lower Frame (Floor)
                {0, -1, 0} // Bottom Center (Player Standing Block)
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
                {1, 2, 0}, {-1, 2, 0}, {0, 2, 1}, {0, 2, -1},
                {0, 2, 0}, {1, -1, 0}, {-1, -1, 0}, {0, -1, 1}, {0, -1, -1},
                {0, -1, 0}
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
