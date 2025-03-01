package me.hamza.pillarsoffortune.hotbar;


import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.List;

/**
 * @author Hammzar
 * @since 01/03/2025
 */

@Getter
@Setter
public class HotbarItem {

    private final String name;
    private final List<String> lore;
    private final Material material;
    private final int durability;
    private final int slot;
    private final String command;

    public HotbarItem(String name, Material material, int durability, int slot, List<String> lore, String command) {
        this.name = name;
        this.material = material;
        this.durability = durability;
        this.slot = slot;
        this.lore = lore;
        this.command = command;
    }



}
