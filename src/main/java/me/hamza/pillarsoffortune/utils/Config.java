package me.hamza.pillarsoffortune.utils;

import lombok.Getter;
import me.hamza.pillarsoffortune.Mortal;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * @author Hammzar
 * @since 17/02/2025
 */

@Getter
public class Config {

    private final File fileConfig;
    private final YamlConfiguration config;

    public Config(String name, Mortal plugin) {
        fileConfig = new File(plugin.getDataFolder(), name);

        if (!fileConfig.exists()) {
            fileConfig.getParentFile().mkdir();
            plugin.saveResource(name, true);
        }

        config = new YamlConfiguration();

        try {
            config.load(fileConfig);
        } catch (Exception e) {
            CC.log(e.getMessage());
        }
    }

    public void save() {
        try {
            config.save(fileConfig);
        } catch (Exception e) {
            CC.log(e.getMessage());
        }
    }

}