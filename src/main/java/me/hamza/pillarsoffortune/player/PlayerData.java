package me.hamza.pillarsoffortune.player;

import lombok.Getter;
import lombok.Setter;
import me.hamza.pillarsoffortune.POF;
import me.hamza.pillarsoffortune.game.Game;
import org.bukkit.Bukkit;

import java.util.UUID;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

@Getter @Setter
public class PlayerData {

    private final UUID uuid;
    private String username;
    private int wins;
    private int losses;
    private int kills;
    private Game game;
    private PlayerState state = PlayerState.LOBBY;

    public PlayerData(UUID cUuid) {
        this.uuid = cUuid;
        this.username = Bukkit.getOfflinePlayer(cUuid).getName();
        this.wins = 0;
        this.losses = 0;
        this.game = null;
    }

    public void addWins(int amount) {
        this.wins += amount;
    }

    public void addLosses(int amount) {
        this.losses += amount;
    }

    public void addKills(int amount) {
        this.kills += amount;
    }

    public void store() {
        POF.getInstance().getPlayerHandler().storeData(this);
    }

    public void load() {
        POF.getInstance().getPlayerHandler().loadData(this);
    }
}

