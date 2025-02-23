package me.hamza.pillarsoffortune.commands.random;

import me.hamza.pillarsoffortune.utils.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandExecutor;
import org.jetbrains.annotations.NotNull;

/**
 * @author Hammzar
 * @since 21/02/2025
 */


//Made this for fun purpose

public class TNTCartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (strings.length != 1) {
                player.sendMessage(CC.color("&cUsage: /tntcart <amount>"));
                return false;
            }

            try {
                int numberOfTNTCarts = Integer.parseInt(strings[0]);

                if (numberOfTNTCarts <= 0 || numberOfTNTCarts > 100) {
                    player.sendMessage(CC.color("&cPlease provide a number between 1 and 100."));
                    return false;
                }

                for (int i = 0; i < numberOfTNTCarts; i++) {
                    player.getWorld().spawnEntity(player.getLocation(), EntityType.TNT_MINECART);
                }

                player.sendMessage(CC.color("&aYou have spawned " + numberOfTNTCarts + " TNT Minecarts."));

            } catch (NumberFormatException e) {
                player.sendMessage(CC.color("&cPlease provide a valid number."));
            }
        } else {
            commandSender.sendMessage(CC.color("&cOnly players can use this command!"));
        }

        return true;
    }

}
