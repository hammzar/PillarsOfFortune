package me.hamza.pillarsoffortune.game;

import lombok.Getter;
import lombok.Setter;
import me.hamza.pillarsoffortune.POF;
import me.hamza.pillarsoffortune.arena.Arena;
import me.hamza.pillarsoffortune.utils.CC;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

@Getter @Setter
public class Game {

    private final Set<GamePlayer> gamePlayers;
    private final GameState state;
    private final int playerSize;
    private Map<Location, BlockData> blockMap;
    private final Arena arena;

    public Game(int cPlayerSize) {
        this.gamePlayers = new HashSet<>();
        this.state = GameState.WAITING;
        this.playerSize = cPlayerSize;
        this.blockMap = new HashMap<>();
        this.arena = POF.getInstance().getArenaHandler().getRandomArena();
        POF.getInstance().getGameHandler().setActiveGame(this);
    }

    public void start() {
        if (POF.getInstance().getGameHandler().getActiveGame() != null) {
            CC.log("&cCannot start game as its already running.");
            return;
        }

    }

    public void end() {
        if (POF.getInstance().getGameHandler().getActiveGame() == null) {
            return;
        }
    }

}
