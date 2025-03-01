package me.hamza.pillarsoffortune.player;

import me.hamza.pillarsoffortune.Mortal;
import me.hamza.pillarsoffortune.game.Game;
import me.hamza.pillarsoffortune.game.GameState;
import me.hamza.pillarsoffortune.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        UUID uniqueID = player.getUniqueId();

        PlayerData playerData = Mortal.getInstance().getPlayerHandler().getPlayer(uniqueID);
        playerData.setUsername(player.getName());

        if (player.isDead()) {
            player.spigot().respawn();
        }

        player.setGameMode(GameMode.SURVIVAL);
        Mortal.getInstance().getHotbarHandler().applyItems(player);

        if (Mortal.getInstance().getLobbyHandler().getLobby() != null) {
            player.teleport(Mortal.getInstance().getLobbyHandler().getLobby());
        } else {
            Bukkit.broadcastMessage(CC.color("&4[ERROR] &cNo lobby has been set!"));
        }
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        Player player = e.getPlayer();
        UUID uniqueId = player.getUniqueId();
        if (Mortal.getInstance().getPlayerHandler().getPlayer(uniqueId) != null) {
            return;
        }

        PlayerData playerData = new PlayerData(uniqueId);
        playerData.load();
        Mortal.getInstance().getPlayerHandler().getPlayerDataMap().put(uniqueId, playerData);

        Game activeGame = Mortal.getInstance().getGameManager().getActiveGame();
        if (activeGame == null) {
            return;
        }

        if (activeGame.getState() == GameState.PLAYING) {
            player.kickPlayer(CC.color("&cGame has already begun."));
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        Mortal.getInstance().getPlayerHandler().getPlayer(e.getPlayer().getUniqueId()).store();
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        Mortal.getInstance().getPlayerHandler().getPlayer(e.getPlayer().getUniqueId()).store();
    }

}
