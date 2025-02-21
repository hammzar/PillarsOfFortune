package me.hamza.pillarsoffortune.game;


import me.hamza.pillarsoffortune.POF;
import me.hamza.pillarsoffortune.utils.CC;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

public class GameListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Game activeGame = POF.getInstance().getGameManager().getActiveGame();


    }
}
