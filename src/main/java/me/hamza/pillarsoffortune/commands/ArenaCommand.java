package me.hamza.pillarsoffortune.commands;

import me.hamza.pillarsoffortune.POF;
import me.hamza.pillarsoffortune.arena.Arena;
import me.hamza.pillarsoffortune.arena.ArenaHandler;
import me.hamza.pillarsoffortune.utils.CC;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

public class ArenaCommand implements CommandExecutor {

    private final ArenaHandler arenaHandler = POF.getInstance().getArenaHandler();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.color("&cOnly players can use this command!"));
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("");
            player.sendMessage(CC.color("&d&lPILLARS OF FORTUNE &7- &dArena Commands"));
            player.sendMessage(CC.color("&7▢ &dUsage: &f/arena add <name> &7- &fAdd an arena"));
            player.sendMessage(CC.color("&7▢ &dUsage: &f/arena remove <name> &7- &fRemove an arena"));
            player.sendMessage(CC.color("&7▢ &dUsage: &f/arena list &7- &fList all arenas"));
            player.sendMessage(CC.color("&7▢ &dUsage: &f/arena teleport <name> &7- &fTeleport to an arena"));
            player.sendMessage(CC.color("&7▢ &dUsage: &f/arena addspawn <name> &7- &fAdd an arena spawn"));
            player.sendMessage("");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "add":
                if (args.length < 2) {
                    player.sendMessage(CC.color("&cUsage: /arena add <name>"));
                    return true;
                }
                addArena(player, args[1]);
                break;

            case "remove":
                if (args.length < 2) {
                    player.sendMessage(CC.color("&cUsage: /arena remove <name>"));
                    return true;
                }
                removeArena(player, args[1]);
                break;

            case "list":
                listArenas(player);
                break;
            case "teleport":
                if (args.length < 2) {
                    player.sendMessage(CC.color("&cUsage: /arena teleport <name>"));
                    return true;
                }
                tpToArena(player, args[1]);
                break;
            case "addspawn":
                if (args.length < 2) {
                    player.sendMessage(CC.color("&cUsage: /arena addspawn <name>"));
                }
                addSpawn(player, args[1]);
            default:
                player.sendMessage("");
                player.sendMessage(CC.color("&d&lPILLARS OF FORTUNE &7- &dArena Commands"));
                player.sendMessage(CC.color("&7▢ &dUsage: &f/arena add <name> &7- &fAdd an arena"));
                player.sendMessage(CC.color("&7▢ &dUsage: &f/arena remove <name> &7- &fRemove an arena"));
                player.sendMessage(CC.color("&7▢ &dUsage: &f/arena list &7- &fList all arenas"));
                player.sendMessage(CC.color("&7▢ &dUsage: &f/arena teleport <name> &7- &fTeleport to an arena"));
                player.sendMessage(CC.color("&7▢ &dUsage: &f/arena addspawn <name> &7- &fAdd an arena spawn"));
                player.sendMessage("");
                break;
        }

        return true;
    }

    private void addArena(Player player, String name) {
        Location center = player.getLocation();
        Arena arena = new Arena(name, center, new ArrayList<>());
        arenaHandler.addArena(arena);
        player.sendMessage(CC.color("&aArena '" + name + "' has been created at your location!"));
    }

    private void removeArena(Player player, String name) {
        Arena arena = arenaHandler.getArena(name);
        if (arena == null) {
            player.sendMessage(CC.color("&cArena not found!"));
            return;
        }
        arenaHandler.removeArena(name);
        player.sendMessage(CC.color("&aArena '" + name + "' has been removed!"));
    }

    private void listArenas(Player player) {
        List<Arena> arenas = arenaHandler.getArenas();
        if (arenas.isEmpty()) {
            player.sendMessage(CC.color("&cThere are no arenas available!"));
            return;
        }

        player.sendMessage(CC.color("&aAvailable Arenas:"));
        for (Arena arena : arenas) {
            player.sendMessage(CC.color("&7- " + arena.getName()));
        }
    }

    private void tpToArena(Player player, String name) {
        Arena arena = arenaHandler.getArena(name);
        if (arena == null) {
            player.sendMessage(CC.color("&cArena not found!"));
            return;
        }
        player.teleport(arena.getCenter());
        player.sendMessage(CC.color("&aTeleported to arena '" + name + "'!"));
    }

    private void addSpawn(Player player, String name) {
        Arena arena = arenaHandler.getArena(name);
        if (arena == null) {
            player.sendMessage(CC.color("&cArena not found!"));
            return;
        }
        arena.getLocations().add(player.getLocation());
        player.sendMessage(CC.color("&aSpawn has been added to arena '" + name + "'!"));
        arenaHandler.saveArena(arena);
    }
}
