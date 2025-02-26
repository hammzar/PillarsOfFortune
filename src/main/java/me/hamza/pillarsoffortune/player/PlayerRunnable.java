package me.hamza.pillarsoffortune.player;


import me.hamza.pillarsoffortune.Mortal;
import me.hamza.pillarsoffortune.game.Game;
import me.hamza.pillarsoffortune.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

public class PlayerRunnable extends BukkitRunnable {

    private final int gameSize = Mortal.getInstance().getSettingsConfiguration().getConfig().getInt("game.size");

    @Override
    public void run() {
        int playersOnTheServer = Mortal.getInstance().getServer().getOnlinePlayers().size();
        List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
        if (onlinePlayers.isEmpty()) {
            return;
        }

        if (playersOnTheServer != this.gameSize) {
            onlinePlayers.forEach(player -> player.sendMessage(CC.color("&eWaiting for " + (gameSize - playersOnTheServer) + " more players...")));
            return;
        }

        Game newGame = new Game(this.gameSize, onlinePlayers);
        newGame.start();
        
        Mortal.getInstance().getGameManager().setActiveGame(newGame);
        onlinePlayers.forEach(player -> player.sendMessage(CC.color("&aGame will start shortly.")));
        cancel();
    }

}
