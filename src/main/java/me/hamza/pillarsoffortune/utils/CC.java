package me.hamza.pillarsoffortune.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<String> color(List<String> messages) {
        return messages.stream().map(CC::color).collect(Collectors.toList());
    }
}
