package me.hamza.pillarsoffortune.commands;

import me.hamza.pillarsoffortune.Mortal;
import me.hamza.pillarsoffortune.lobby.LobbyHandler;
import me.hamza.pillarsoffortune.utils.CC;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetLobbyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.color("&cOnly players can use this command!"));
            return false;
        }

        Player player = (Player) sender;
        Location playerLocation = player.getLocation();
        Mortal.getInstance().getLobbyHandler().changeLobbyLocation(playerLocation);
        player.sendMessage(CC.color("&aYou have set the lobby location to " + playerLocation.getBlockX() + " " + playerLocation.getBlockY() + " " + playerLocation.getBlockZ()));
        return true;
    }

}
