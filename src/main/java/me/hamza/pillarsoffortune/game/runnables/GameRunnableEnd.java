package me.hamza.pillarsoffortune.game.runnables;

import me.hamza.pillarsoffortune.Mortal;
import me.hamza.pillarsoffortune.game.Game;
import me.hamza.pillarsoffortune.game.GameState;
import me.hamza.pillarsoffortune.player.PlayerData;
import me.hamza.pillarsoffortune.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

public class GameRunnableEnd extends BukkitRunnable {

    private int celebrationTicks = 100;

    @Override
    public void run() {
        Game activeGame = Mortal.getInstance().getGameManager().getActiveGame();

        if (activeGame == null) {
            cancel();
            return;
        }

        UUID winnerUUID = activeGame.getWinner();
        if (winnerUUID == null) {
            return;
        }

        Player winnerPlayer = Bukkit.getPlayer(activeGame.getWinner());

        if (winnerPlayer != null) {
            PlayerData playerData = Mortal.getInstance().getPlayerHandler().getPlayer(winnerPlayer.getUniqueId());
            playerData.setWins(playerData.getWins() + 1);

            activeGame.setState(GameState.ENDING);

            Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(CC.color("&a" + winnerPlayer.getName() + " has won the game!")));

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (celebrationTicks <= 0) {
                        activeGame.end();
                        cancel();
                        return;
                    }

                    launchFirework(winnerPlayer);
                    celebrationTicks -= 5;
                }
            }.runTaskTimer(Mortal.getInstance(), 0, 20);
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
        return colors[ThreadLocalRandom.current().nextInt(colors.length)];
    }

}
