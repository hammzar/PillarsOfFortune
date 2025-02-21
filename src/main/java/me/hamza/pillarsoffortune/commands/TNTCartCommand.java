package me.hamza.pillarsoffortune.commands;

import me.hamza.pillarsoffortune.POF;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandExecutor;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

public class TNTCartCommand implements CommandExecutor {

    // Constructor
    public TNTCartCommand() {
        // Register the command when the plugin is loaded
        POF.getInstance().getCommand("tntcart").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if the sender is a player
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // Check if the command has arguments (the number of TNT carts to spawn)
            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "Usage: /tntcart <number>");
                return false;
            }

            // Try to parse the number of TNT carts
            try {
                int numberOfTNTCarts = Integer.parseInt(args[0]);

                // Validate the number (you can adjust the limits as needed)
                if (numberOfTNTCarts <= 0 || numberOfTNTCarts > 100) {
                    player.sendMessage(ChatColor.RED + "Please enter a number between 1 and 100.");
                    return false;
                }

                // Spawn the TNT carts
                for (int i = 0; i < numberOfTNTCarts; i++) {
                    player.getWorld().spawnEntity(player.getLocation(), EntityType.TNT_MINECART);
                }

                player.sendMessage(ChatColor.GREEN + "Successfully spawned " + numberOfTNTCarts + " TNT minecarts!");

            } catch (NumberFormatException e) {
                player.sendMessage(ChatColor.RED + "Please provide a valid number.");
            }
        } else {
            // If the sender is not a player (e.g., the console)
            sender.sendMessage(ChatColor.RED + "This command can only be run by a player.");
        }

        return true;
    }
}
