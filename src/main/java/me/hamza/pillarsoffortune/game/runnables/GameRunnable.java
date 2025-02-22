package me.hamza.pillarsoffortune.game.runnables;


import me.hamza.pillarsoffortune.POF;
import me.hamza.pillarsoffortune.game.Game;
import me.hamza.pillarsoffortune.game.GameState;
import me.hamza.pillarsoffortune.utils.CC;
import me.hamza.pillarsoffortune.utils.GlassCageUtils;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

public class GameRunnable extends BukkitRunnable {

    private int cooldownTicks = 100;

    @Override
    public void run() {
        Game activeGame = POF.getInstance().getGameManager().getActiveGame();

        if (activeGame == null) {
            cancel();
            return;
        }

        if (activeGame.getGamePlayers().size() < activeGame.getPlayerSize()) {
            return;
        }

        if (cooldownTicks > 0) {
            cooldownTicks--;

            if (cooldownTicks % 20 == 0) {
                int secondsRemaining = cooldownTicks / 20 + 1;
                String timeMessage = (secondsRemaining == 1) ? "second" : "seconds";

                activeGame.getGamePlayers().forEach(player ->
                        player.sendMessage(CC.color("&cGame will start in " + secondsRemaining + " " + timeMessage))
                );
            }

            if (cooldownTicks == 0) {
                activeGame.setState(GameState.PLAYING);
                activeGame.getGamePlayers().forEach(player ->
                        player.sendMessage(CC.color("&aGame has started! You can now move freely."))
                );

                activeGame.getUsedSpawns().forEach(GlassCageUtils::removeGlassCage);

                new GameRunnableEnd().runTaskTimer(POF.getInstance(), 0, 20L);
            }
        }
    }
}
