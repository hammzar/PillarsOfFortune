package me.hamza.pillarsoffortune.commands;


import me.hamza.pillarsoffortune.POF;
import me.hamza.pillarsoffortune.utils.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage("");
        commandSender.sendMessage(CC.color("&d&lPILLARS OF FORTUNE &7made by &d&lHAMMZAR"));
        commandSender.sendMessage(CC.color("&7▢ &dVersion: &f" + POF.getInstance().getDescription().getVersion()));
        commandSender.sendMessage(CC.color("&7▢ &dDescription: &f" + POF.getInstance().getDescription().getDescription()));
        commandSender.sendMessage(CC.color("&7▢ &dDiscord: &fdiscord.gg/WaqWWBWwMG"));
        commandSender.sendMessage("");
        return true;
    }
}
