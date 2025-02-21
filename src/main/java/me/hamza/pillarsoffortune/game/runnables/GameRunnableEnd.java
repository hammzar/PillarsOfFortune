package me.hamza.pillarsoffortune.game.runnables;

import me.hamza.pillarsoffortune.POF;
import me.hamza.pillarsoffortune.game.Game;
import me.hamza.pillarsoffortune.game.GameState;
import me.hamza.pillarsoffortune.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.Random;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

public class GameRunnableEnd extends BukkitRunnable {

    private int celebrationTicks = 100; // 5 seconds (100 ticks)
    private final Random random = new Random();

    @Override
    public void run() {
        Game activeGame = POF.getInstance().getGameManager().getActiveGame();

        if (activeGame == null) {
            cancel();
            return;
        }

        Player winnerPlayer = Bukkit.getPlayer(activeGame.getWinner());

        if (winnerPlayer != null) {
            activeGame.setState(GameState.ENDING);
            activeGame.getGamePlayers().forEach(gamePlayer ->
                    gamePlayer.sendMessage(CC.color("&a" + winnerPlayer.getName() + " has won the game!"))
            );

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (celebrationTicks <= 0) {
                        activeGame.end();
                        cancel();
                        return;
                    }

                    launchFirework(winnerPlayer);
                    celebrationTicks -= 20;
                }
            }.runTaskTimer(POF.getInstance(), 0, 20);
        }
    }

    private void launchFirework(Player player) {
        Firework firework = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK_ROCKET);
        FireworkMeta meta = firework.getFireworkMeta();

        FireworkEffect effect = FireworkEffect.builder()
                .withColor(getRandomColor(), getRandomColor())
                .with(FireworkEffect.Type.BALL_LARGE)
                .withTrail()
                .withFlicker()
                .build();

        meta.addEffect(effect);
        meta.setPower(1);
        firework.setFireworkMeta(meta);
    }

    private Color getRandomColor() {
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PURPLE, Color.ORANGE, Color.WHITE};
        return colors[random.nextInt(colors.length)];
    }
}
