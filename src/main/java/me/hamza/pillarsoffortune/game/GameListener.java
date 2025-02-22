package me.hamza.pillarsoffortune.game;


import me.hamza.pillarsoffortune.POF;
import me.hamza.pillarsoffortune.player.PlayerData;
import me.hamza.pillarsoffortune.player.PlayerState;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

public class GameListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();

        Game game = POF.getInstance().getGameManager().getActiveGame();
        if (game == null) {
            return;
        }

        Player killer = e.getEntity().getKiller();
        if (killer == null) {
            game.onDeath(player, null);
            return;
        }


        game.onDeath(player, killer);
    }

    @EventHandler
    public void onBlockBreaking(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Game game = POF.getInstance().getGameManager().getActiveGame();
        Block block = e.getBlock();
        if (game == null) {
            return;
        }

        PlayerData playerData = POF.getInstance().getPlayerHandler().getPlayer(player.getUniqueId());
        if (playerData.getState() != PlayerState.PLAYING) {
            e.setCancelled(true);
        }

        if (game.getGamePlayers().contains(player)) {
            if (game.getState() != GameState.PLAYING) {
                e.setCancelled(true);
                return;
            }

            Location location = block.getLocation();
            game.getBlockMap().remove(location);
        }
    }

    @EventHandler
    public void onBlockPLacing(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Game game = POF.getInstance().getGameManager().getActiveGame();
        Block block = e.getBlockPlaced();
        if (game == null) {
            return;
        }

        PlayerData playerData = POF.getInstance().getPlayerHandler().getPlayer(player.getUniqueId());
        if (playerData.getState() != PlayerState.PLAYING) {
            e.setCancelled(true);
        }

        if (game.getGamePlayers().contains(player)) {
            if (game.getState() != GameState.PLAYING) {
                e.setCancelled(true);
                return;
            }

            Location location = block.getLocation();
            game.getBlockMap().put(location, location.getBlock().getBlockData());
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByBlockEvent e) {
        Player player = (Player) e.getEntity();
        Game game = POF.getInstance().getGameManager().getActiveGame();
        if (game == null) {
            return;
        }

        PlayerData playerData = POF.getInstance().getPlayerHandler().getPlayer(player.getUniqueId());
        if (playerData.getState() != PlayerState.PLAYING || game.getState() != GameState.PLAYING) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent e) {
        Player player = (Player) e.getEntity();
        Game game = POF.getInstance().getGameManager().getActiveGame();
        if (game == null) {
            return;
        }

        PlayerData playerData = POF.getInstance().getPlayerHandler().getPlayer(player.getUniqueId());
        if (playerData.getState() != PlayerState.PLAYING || game.getState() != GameState.PLAYING) {
            e.setCancelled(true);
        }
    }
}
