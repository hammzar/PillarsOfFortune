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
            player.sendMessage(CC.color("&cUsage: /arena <add|remove|list|teleport>"));
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

            default:
                player.sendMessage(CC.color("&cUsage: /arena <add|remove|list|teleport>"));
                break;
        }

        return true;
    }

    private void addArena(Player player, String name) {
        Location center = player.getLocation();
        Arena arena = new Arena(name, center, List.of(center));
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
}
