package me.hamza.pillarsoffortune.game;

import lombok.Getter;
import lombok.Setter;
import me.hamza.pillarsoffortune.Mortal;
import me.hamza.pillarsoffortune.arena.Arena;
import me.hamza.pillarsoffortune.game.runnables.GameRunnable;
import me.hamza.pillarsoffortune.player.PlayerData;
import me.hamza.pillarsoffortune.player.PlayerState;
import me.hamza.pillarsoffortune.utils.CC;
import me.hamza.pillarsoffortune.utils.GlassCageUtils;
import me.hamza.pillarsoffortune.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

@Getter
@Setter
public class Game {

    protected final Set<GamePlayer> gamePlayers;
    private GameState state;
    private final int playerSize;
    private Map<Location, BlockData> blockMap;
    private final Arena arena;
    private Set<Location> usedSpawns;
    private UUID winner;

    public Game(int cPlayerSize, List<Player> cPlayers) {
        this.gamePlayers = new HashSet<>();
        this.state = GameState.WAITING;
        this.playerSize = cPlayerSize;
        this.blockMap = new HashMap<>();
        this.arena = Mortal.getInstance().getArenaHandler().getRandomArena();
        this.usedSpawns = new HashSet<>();
        cPlayers.forEach(player -> this.gamePlayers.add(new GamePlayer(player.getUniqueId())));
        Mortal.getInstance().getGameManager().setActiveGame(this);
    }

    public void start() {
        usedSpawns.clear();

        for (GamePlayer gamePlayer : gamePlayers) {
            Player player = gamePlayer.getPlayer();
            Location spawn = getUniqueSpawnLocation();

            if (spawn == null) {
                Bukkit.getConsoleSender().sendMessage("§cDebug: Could not find a spawn location for player " + player.getName());
                return;
            } else {
                GlassCageUtils.createGlassCage(spawn);
                player.teleport(spawn);
            }
        }

        getGamePlayers().forEach(player -> {
            PlayerData playerData = Mortal.getInstance().getPlayerHandler().getPlayer(player.getUniqueId());
            playerData.setState(PlayerState.PLAYING);
        });

        getGamePlayers().forEach(player -> PlayerUtils.reset(player, true));
        state = GameState.STARTING;
        GameRunnable gameRunnable = new GameRunnable();
        gameRunnable.runTaskTimer(Mortal.getInstance(), 0, 1L);
    }

    public void end() {
        Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer(CC.color("&cGame has ended.")));
        Bukkit.shutdown();
    }

    public List<Player> getGamePlayers() {
        return gamePlayers.stream().map(GamePlayer::getPlayer).collect(Collectors.toList());
    }

    private Location getUniqueSpawnLocation() {
        List<Location> availablePositions = arena.getLocations().stream()
                .filter(location -> !usedSpawns.contains(location))
                .collect(Collectors.toList());
        if (availablePositions.isEmpty()) {
            return null;
        }

        Location location = availablePositions.get(ThreadLocalRandom.current().nextInt(availablePositions.size()));
        usedSpawns.add(location);
        blockMap.put(location, location.getBlock().getBlockData());
        return location;
    }

    public GamePlayer getGamePlayer(UUID uuid) {
        for (GamePlayer gamePlayer : gamePlayers) {
            if (gamePlayer.getUuid().equals(uuid)) {
                return gamePlayer;
            }
        }
        return null;
    }

    public void onDeath(Player player, Player killer) {
        GamePlayer gamePlayer = getGamePlayer(player.getUniqueId());
        if (gamePlayer == null || gamePlayer.isDead()) {
            return;
        }

        Mortal.getInstance().getServer().getScheduler().runTaskLater(Mortal.getInstance(), () -> player.spigot().respawn(), 1L);
        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(getArena().getCenter());

        gamePlayer.setDead(true);
        this.gamePlayers.remove(gamePlayer);

        List<GamePlayer> alivePlayers = gamePlayers.stream()
                .filter(gameP -> !gameP.isDead())
                .collect(Collectors.toList());

        if (alivePlayers.size() == 1) {
            winner = alivePlayers.get(0).getUuid();
        }

        if (killer != null) {
            PlayerData playerData = Mortal.getInstance().getPlayerHandler().getPlayer(killer.getUniqueId());
            playerData.addKills(1);
            getGamePlayers().forEach(player1 -> player1.sendMessage(CC.color("&c" + player.getName() + " has been eliminated by " + killer.getName() + "!")));
        }

        getGamePlayers().forEach(player1 -> player1.sendMessage(CC.color("&c" + player.getName() + " has been eliminated!")));
        PlayerData playerData = Mortal.getInstance().getPlayerHandler().getPlayer(player.getUniqueId());
        playerData.addLosses(1);
    }

    public void handleQuit(Player player) {
        if (getGamePlayer(player.getUniqueId()) == null) {
            return;
        }

        getGamePlayer(player.getUniqueId()).setDead(true);
        gamePlayers.remove(getGamePlayer(player.getUniqueId()));
        getGamePlayers().forEach(player1 -> player1.sendMessage(CC.color("&c" + player.getName() + " has been eliminated because they left!")));
    }
}
