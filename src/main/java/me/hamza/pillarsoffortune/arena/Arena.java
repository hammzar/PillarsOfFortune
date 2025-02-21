package me.hamza.pillarsoffortune.arena;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

@Getter @Setter
public class Arena {

    private String name;
    private Location center;
    private List<Location> locations;

    public Arena(String cName, Location cCenter, List<Location> cLocation) {
        this.name = cName;
        this.center = cCenter;
        this.locations = cLocation;
    }

}
