package me.hamza.pillarsoffortune.arena;

import lombok.Getter;
import me.hamza.pillarsoffortune.POF;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

@Getter
public class ArenaHandler {

    private final List<Arena> arenas = new ArrayList<>();
    private final FileConfiguration configuration = POF.getInstance().getArenaConfiguration().getConfig();

    public void init() {
        if (configuration.getConfigurationSection("arenas") == null) {
            return;
        }

        for (String arenaName : Objects.requireNonNull(configuration.getConfigurationSection("arenas")).getKeys(false)) {
            ConfigurationSection arenaSection = configuration.getConfigurationSection("arenas." + arenaName);
            if (arenaSection == null) continue;

            Location center = (Location) arenaSection.get("center");
            List<Location> spawnLocations = new ArrayList<>();
            List<?> rawList = arenaSection.getList("spawnPoints");

            if (rawList != null) {
                for (Object obj : rawList) {
                    if (obj instanceof Location) {
                        spawnLocations.add((Location) obj);
                    }
                }
            }

            arenas.add(new Arena(arenaName, center, spawnLocations));
        }
    }

    public void addArena(Arena arena) {
        arenas.add(arena);
        saveArena(arena);
    }

    public void removeArena(String name) {
        Arena arena = getArena(name);
        arenas.remove(arena);
        configuration.set("arenas." + name, null);
        POF.getInstance().getArenaConfiguration().save();
    }

    public Arena getArena(String name) {
        return arenas.stream().filter(a -> a.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Arena getRandomArena() {
        if (arenas.isEmpty()) {
            return null;
        }
        return arenas.get(ThreadLocalRandom.current().nextInt(arenas.size()));
    }

    public void saveArena(Arena arena) {
        String path = "arenas." + arena.getName();
        configuration.set(path + ".center", arena.getCenter());
        configuration.set(path + ".spawnPoints", arena.getLocations());

        POF.getInstance().getArenaConfiguration().save();
    }

}
