package me.hamza.pillarsoffortune.hotbar;


import lombok.Getter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Hammzar
 * @since 01/03/2025
 */

@Getter
public enum HotbarItems {

    VOTE(
            "&d&lVote Scenario &f(Right-Click)",
            Material.NETHER_STAR,
            0, 0,
            Arrays.asList("&7Click to vote for a scenario!", "&7Right-click to see options."),
            "vote"
    ),

    RETURN_TO_HUB(
            "&d&lReturn to Hub &f(Right-Click)",
            Material.RED_DYE,
            0, 8,
            Arrays.asList("&7Click to return to the hub!"),
            "hub"
    );

    private final String name;
    private final List<String> lore;
    private final Material material;
    private final int durability;
    private final int slot;
    private final String command;

    HotbarItems(String name, Material material, int durability, int slot, List<String> lore, String command) {
        this.name = name;
        this.material = material;
        this.durability = durability;
        this.slot = slot;
        this.lore = lore;
        this.command = command;
    }
}
