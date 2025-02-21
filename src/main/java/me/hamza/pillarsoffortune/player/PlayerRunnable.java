package me.hamza.pillarsoffortune.player;


import me.hamza.pillarsoffortune.POF;
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

    private final int gameSize = POF.getInstance().getSettingsConfiguration().getConfig().getInt("game.size");

    @Override
    public void run() {
        int playersOnTheServer = POF.getInstance().getServer().getOnlinePlayers().size();
        List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
        if (onlinePlayers.isEmpty()) {
            return;
        }

        if (playersOnTheServer != this.gameSize) {
            onlinePlayers.forEach(player -> player.sendMessage(CC.color("&eWaiting for " + (gameSize - playersOnTheServer) + " more players...")));
            return;
        }

        Bukkit.broadcastMessage("§cDebug: Starting game");
        Game newGame = new Game(this.gameSize, onlinePlayers);
        newGame.start();

        Bukkit.broadcastMessage("§cDebug: Setting active game");
        POF.getInstance().getGameManager().setActiveGame(newGame);

        onlinePlayers.forEach(player -> player.sendMessage(CC.color("&aGame will start shortly.")));
        Bukkit.broadcastMessage("§cDebug: Cancelling runnable");
        cancel();
    }
}
