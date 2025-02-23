package me.hamza.pillarsoffortune.player;

import me.hamza.pillarsoffortune.Mortal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
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
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        Mortal.getInstance().getPlayerHandler().getPlayer(e.getPlayer().getUniqueId()).store();
    }
}
