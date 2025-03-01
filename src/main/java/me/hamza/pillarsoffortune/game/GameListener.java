package me.hamza.pillarsoffortune.game;


import me.hamza.pillarsoffortune.Mortal;
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
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

public class GameListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        PlayerData playerData = Mortal.getInstance().getPlayerHandler().getPlayer(player.getUniqueId());
        if (playerData.getState() != PlayerState.PLAYING) {
            return;
        }

        Game game = Mortal.getInstance().getGameManager().getActiveGame();
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
        PlayerData playerData = Mortal.getInstance().getPlayerHandler().getPlayer(player.getUniqueId());
        if (playerData.isBuildMode()) {
            return;
        }

        if (playerData.getState() != PlayerState.PLAYING) {
            e.setCancelled(true);
        }

        Game game = Mortal.getInstance().getGameManager().getActiveGame();
        Block block = e.getBlock();
        if (game == null) {
            return;
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
        PlayerData playerData = Mortal.getInstance().getPlayerHandler().getPlayer(player.getUniqueId());
        if (playerData.isBuildMode()) {
            return;
        }

        if (playerData.getState() != PlayerState.PLAYING) {
            e.setCancelled(true);
        }

        Game game = Mortal.getInstance().getGameManager().getActiveGame();
        if (game == null) {
            return;
        }

        Block block = e.getBlockPlaced();

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
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getEntity();

        Game game = Mortal.getInstance().getGameManager().getActiveGame();
        if (game == null) {
            e.setCancelled(true);
            return;
        }

        PlayerData playerData = Mortal.getInstance().getPlayerHandler().getPlayer(player.getUniqueId());
        if (playerData.getState() != PlayerState.PLAYING || game.getState() != GameState.PLAYING) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getEntity();

        Game game = Mortal.getInstance().getGameManager().getActiveGame();
        if (game == null) {
            e.setCancelled(true);
            return;
        }

        PlayerData playerData = Mortal.getInstance().getPlayerHandler().getPlayer(player.getUniqueId());
        if (playerData.getState() != PlayerState.PLAYING || game.getState() != GameState.PLAYING) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onFallDmg(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Game game = Mortal.getInstance().getGameManager().getActiveGame();
        if (game == null) {
            e.setCancelled(true);
            return;
        }

        if (game.getState() != GameState.PLAYING) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        PlayerData playerData = Mortal.getInstance().getPlayerHandler().getPlayer(e.getPlayer().getUniqueId());
        if (playerData.getState() == PlayerState.PLAYING) {
            Game game = Mortal.getInstance().getGameManager().getActiveGame();
            if (game != null) {
                game.handleQuit(e.getPlayer());
            }
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        PlayerData playerData = Mortal.getInstance().getPlayerHandler().getPlayer(e.getPlayer().getUniqueId());
        if (playerData.getState() == PlayerState.PLAYING) {
            Game game = Mortal.getInstance().getGameManager().getActiveGame();
            if (game != null) {
                game.handleQuit(e.getPlayer());
            }
        }
    }

}
