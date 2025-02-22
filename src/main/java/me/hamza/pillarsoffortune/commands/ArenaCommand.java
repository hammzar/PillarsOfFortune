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
            player.sendMessage(CC.color("&7▢ &dUsage: &f/arena create <name> &7- &fAdd an arena"));
            player.sendMessage(CC.color("&7▢ &dUsage: &f/arena remove <name> &7- &fRemove an arena"));
            player.sendMessage(CC.color("&7▢ &dUsage: &f/arena list &7- &fList all arenas"));
            player.sendMessage(CC.color("&7▢ &dUsage: &f/arena teleport <name> &7- &fTeleport to an arena"));
            player.sendMessage(CC.color("&7▢ &dUsage: &f/arena addspawn <name> &7- &fAdd an arena spawn"));
            player.sendMessage(CC.color("&7▢ &dUsage: &f/arena setcenter <name> &7- &fSet the arena center"));
            player.sendMessage("");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "create":
                if (args.length < 2) {
                    player.sendMessage(CC.color("&cUsage: /arena create <name>"));
                    return true;
                }
                createArena(player, args[1]);
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
            case "setcenter":
                if (args.length < 2) {
                    player.sendMessage(CC.color("&cUsage: /arena setcenter <name>"));
                }
                setCenter(player, args[1]);
            case "addspawn":
                if (args.length < 2) {
                    player.sendMessage(CC.color("&cUsage: /arena addspawn <name>"));
                }
                addSpawn(player, args[1]);
            default:
                player.sendMessage("");
                player.sendMessage(CC.color("&d&lPILLARS OF FORTUNE &7- &dArena Commands"));
                player.sendMessage(CC.color("&7▢ &dUsage: &f/arena create <name> &7- &fAdd an arena"));
                player.sendMessage(CC.color("&7▢ &dUsage: &f/arena remove <name> &7- &fRemove an arena"));
                player.sendMessage(CC.color("&7▢ &dUsage: &f/arena list &7- &fList all arenas"));
                player.sendMessage(CC.color("&7▢ &dUsage: &f/arena teleport <name> &7- &fTeleport to an arena"));
                player.sendMessage(CC.color("&7▢ &dUsage: &f/arena addspawn <name> &7- &fAdd an arena spawn"));
                player.sendMessage("");
                break;
        }

        return true;
    }

    private void createArena(Player player, String name) {
        Location center = player.getLocation();
        Arena arena = new Arena(name, center, new ArrayList<>());
        arenaHandler.createArena(arena);
        player.sendMessage(CC.color("&aArena '" + name + "' has been created!"));
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

        player.sendMessage("");
        player.sendMessage(CC.color("&d&lPILLARS OF FORTUNE &7- &dArenas"));
        for (Arena arena : arenas) {
            player.sendMessage(CC.color("&7- &d" + arena.getName()));
        }
        player.sendMessage("");
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

        if (player.getLocation() == arena.getCenter()) {
            player.sendMessage(CC.color("&cYou cannot add the arena center as a spawn!"));
            return;
        }

        arena.getLocations().add(player.getLocation());
        player.sendMessage(CC.color("&aSpawn has been added to arena '" + name + "'!"));
        arenaHandler.saveArena(arena);
    }

    private void setCenter(Player player, String name) {
        Arena arena = arenaHandler.getArena(name);
        if (arena == null) {
            player.sendMessage(CC.color("&cArena not found!"));
            return;
        }
        arena.setCenter(player.getLocation());
        player.sendMessage(CC.color("&aCenter has been set to arena '" + name + "'!"));
        arenaHandler.saveArena(arena);
    }
}
