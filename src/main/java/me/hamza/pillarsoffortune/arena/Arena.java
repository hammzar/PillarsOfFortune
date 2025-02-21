package me.hamza.pillarsoffortune.arena;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.List;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

@AllArgsConstructor
@Getter @Setter
public class Arena {

    private String name;
    private Location center;
    private List<Location> locations;

}
