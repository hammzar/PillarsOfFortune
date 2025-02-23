package me.hamza.pillarsoffortune.arena;

import lombok.Getter;
import me.hamza.pillarsoffortune.Mortal;
import me.hamza.pillarsoffortune.utils.ObjectSerializer;
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
    private final FileConfiguration configuration = Mortal.getInstance().getArenaConfiguration().getConfig();

    public void init() {
        if (configuration.getConfigurationSection("arenas") == null) {
            return;
        }

        for (String arenaName : Objects.requireNonNull(configuration.getConfigurationSection("arenas")).getKeys(false)) {
            ConfigurationSection arenaSection = configuration.getConfigurationSection("arenas." + arenaName);
            if (arenaSection == null) continue;

            String centerString = arenaSection.getString("center");
            Location center = ObjectSerializer.deserializeLocation(Objects.requireNonNull(centerString));

            List<String> rawSpawnLocations = arenaSection.getStringList("spawnPoints");
            List<Location> spawnLocations = new ArrayList<>();
            for (String spawnString : rawSpawnLocations) {
                Location spawn = ObjectSerializer.deserializeLocation(spawnString);
                if (spawn != null) {
                    spawnLocations.add(spawn);
                }
            }

            arenas.add(new Arena(arenaName, center, spawnLocations));
        }
    }

    public void createArena(Arena arena) {
        arenas.add(arena);
        saveArena(arena);
    }

    public void removeArena(String name) {
        Arena arena = getArena(name);
        arenas.remove(arena);
        configuration.set("arenas." + name, null);
        Mortal.getInstance().getArenaConfiguration().save();
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

        configuration.set(path + ".center", ObjectSerializer.serializeLocation(arena.getCenter()));

        List<String> serializedSpawnPoints = new ArrayList<>();
        for (Location spawn : arena.getLocations()) {
            serializedSpawnPoints.add(ObjectSerializer.serializeLocation(spawn));
        }
        configuration.set(path + ".spawnPoints", serializedSpawnPoints);

        Mortal.getInstance().getArenaConfiguration().save();
    }

}
