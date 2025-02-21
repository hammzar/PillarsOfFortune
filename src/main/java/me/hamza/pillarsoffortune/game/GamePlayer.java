package me.hamza.pillarsoffortune.game;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

@Getter @Setter
public class GamePlayer {

    private final UUID uuid;
    private final Player player;
    private boolean dead;

    public GamePlayer(UUID cUuid) {
        this.uuid = cUuid;
        this.player = Bukkit.getPlayer(cUuid);
        this.dead = false;
    }



}
