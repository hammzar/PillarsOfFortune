package me.hamza.pillarsoffortune.game;

import lombok.Getter;
import lombok.Setter;
import me.hamza.pillarsoffortune.POF;
import me.hamza.pillarsoffortune.arena.Arena;
import me.hamza.pillarsoffortune.game.runnables.GameRunnable;
import me.hamza.pillarsoffortune.utils.CC;
import me.hamza.pillarsoffortune.utils.GlassCageUtils;
import me.hamza.pillarsoffortune.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

@Getter @Setter
public class Game {

    protected final Set<GamePlayer> gamePlayers;
    private GameState state;
    private final int playerSize;
    private Map<Location, BlockData> blockMap;
    private final Arena arena;
    private Set<Location> usedSpawns;
    private UUID winner;

    private HashMap<Location, BlockData> blockMapBackup;

    public Game(int cPlayerSize, List<Player> cPlayers) {
        this.gamePlayers = new HashSet<>();
        this.state = GameState.WAITING;
        this.playerSize = cPlayerSize;
        this.blockMap = new HashMap<>();
        this.arena = POF.getInstance().getArenaHandler().getRandomArena();
        this.usedSpawns = new HashSet<>();
        cPlayers.forEach(player -> this.gamePlayers.add(new GamePlayer(player.getUniqueId())));
        POF.getInstance().getGameManager().setActiveGame(this);
    }

    public void start() {
        Bukkit.broadcastMessage("§cDebug: Game started with " + gamePlayers.size() + " players.");
        usedSpawns.clear();

        for (GamePlayer gamePlayer : gamePlayers) {
            Bukkit.broadcastMessage("§cDebug: Teleporting player " + gamePlayer.getPlayer().getName());
            Player player = gamePlayer.getPlayer();
            Location spawn = getUniqueSpawnLocation();

            if (spawn == null) {
                Bukkit.broadcastMessage("§cDebug: Could not find a spawn location for player " + player.getName());
                return;
            } else {
                Bukkit.broadcastMessage("§cDebug: Teleporting player " + player.getName() + " to " + spawn.toString());
                player.teleport(spawn);
                GlassCageUtils.createGlassCage(spawn);
            }
        }

        Bukkit.broadcastMessage("§cDebug: Resetting players");
        getGamePlayers().forEach(player -> PlayerUtils.reset(player, true));

        Bukkit.broadcastMessage("§cDebug: Starting game");
        state = GameState.STARTING;

        Bukkit.broadcastMessage("§cDebug: Starting game runnable");
        GameRunnable gameRunnable = new GameRunnable();
        gameRunnable.runTaskTimer(POF.getInstance(), 0, 1L);
    }

    public void end() {
        if (POF.getInstance().getGameManager().getActiveGame() == null) {
            return;
        }
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

    public void onDeath(Player player) {
        GamePlayer gamePlayer = getGamePlayer(player.getUniqueId());

        if (gamePlayer == null || gamePlayer.isDead()) {
            return;
        }

        gamePlayer.setDead(true);
        getGamePlayers().forEach(player1 -> player1.sendMessage(CC.color("&c" + player.getName() + " has been eliminated!")));

        List<GamePlayer> alivePlayers = gamePlayers.stream()
                .filter(gameP -> !gameP.isDead())
                .collect(Collectors.toList());

        if (alivePlayers.size() == 1) {
            winner = alivePlayers.get(0).getUuid();
        }
    }
}
