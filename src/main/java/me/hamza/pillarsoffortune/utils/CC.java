package me.hamza.pillarsoffortune.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

public class CC {

    public static String color (String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static void log(String text) {
        Bukkit.getConsoleSender().sendMessage(color(text));
    }

}
