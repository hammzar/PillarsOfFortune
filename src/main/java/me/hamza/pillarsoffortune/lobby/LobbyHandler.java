package me.hamza.pillarsoffortune.lobby;


import lombok.Getter;
import me.hamza.pillarsoffortune.Mortal;
import me.hamza.pillarsoffortune.utils.ObjectSerializer;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

/**
 * @author Hammzar
 * @since 01/03/2025
 */

@Getter
public class LobbyHandler {

    private Location lobby;

    public void init() {
        FileConfiguration configuration = Mortal.getInstance().getSettingsConfiguration().getConfig();
        if (configuration.getString("lobby-location") == null) return;
        lobby = ObjectSerializer.deserializeLocation(Objects.requireNonNull(configuration.getString("lobby-location")));
    }

    public void changeLobbyLocation(Location lobby) {
        this.lobby = lobby;
        saveLobbyLocation(lobby);
    }

    public void saveLobbyLocation(Location lobby) {
        FileConfiguration configuration = Mortal.getInstance().getSettingsConfiguration().getConfig();
        configuration.set("lobby-location", ObjectSerializer.serializeLocation(lobby));
        Mortal.getInstance().getSettingsConfiguration().save();
    }
}
