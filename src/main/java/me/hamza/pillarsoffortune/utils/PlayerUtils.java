package me.hamza.pillarsoffortune.utils;


import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

public class PlayerUtils {

    public static void reset(Player player, boolean setGameMode) {
        player.getInventory().clear();
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setSaturation(0);
        player.setFireTicks(0);
        player.setExp(0);
        player.setLevel(0);
        player.setTotalExperience(0);
        player.closeInventory();

        if (setGameMode) {
            player.setGameMode(GameMode.SURVIVAL);
        }
    }
}
