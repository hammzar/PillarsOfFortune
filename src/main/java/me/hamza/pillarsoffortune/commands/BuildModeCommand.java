package me.hamza.pillarsoffortune.commands;

import me.hamza.pillarsoffortune.Mortal;
import me.hamza.pillarsoffortune.player.PlayerData;
import me.hamza.pillarsoffortune.utils.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author Hammzar
 * @since 23/02/2025
 */

public class BuildModeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(CC.color("&cOnly players can use this command."));
            return false;
        }

        Player player = (Player) commandSender;
        PlayerData playerData = Mortal.getInstance().getPlayerHandler().getPlayer(player.getUniqueId());

        if (playerData == null) {
            player.sendMessage(CC.color("&cPlayer data not found."));
            return false;
        }

        playerData.setBuildMode(!playerData.isBuildMode());

        if (playerData.isBuildMode()) {
            player.sendMessage(CC.color("&aBuild mode enabled! You can now place blocks."));
        } else {
            player.sendMessage(CC.color("&cBuild mode disabled! You can no longer place blocks."));
        }

        return true;
    }
}