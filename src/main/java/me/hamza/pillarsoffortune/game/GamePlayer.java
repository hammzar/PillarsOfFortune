package me.hamza.pillarsoffortune.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

@AllArgsConstructor
@Getter @Setter
public class GamePlayer {

    private final UUID uuid;
    private final Player player;
    private boolean dead;

}
