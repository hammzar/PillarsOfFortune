package me.hamza.pillarsoffortune.item;

import lombok.Getter;
import org.bukkit.Material;

import java.util.EnumSet;
import java.util.Set;

/**
 * @author Hammzar
 * @since 20/02/2025
 */

@Getter
public class ItemOrganizer {

    private final Set<Material> blocks;
    private final Set<Material> tools;
    private final Set<Material> food;
    private final Set<Material> misc;

    public ItemOrganizer() {
        this.blocks = EnumSet.noneOf(Material.class);
        this.tools = EnumSet.noneOf(Material.class);
        this.food = EnumSet.noneOf(Material.class);
        this.misc = EnumSet.noneOf(Material.class);
    }

    public void init() {
        for (Material material : Material.values()) {
            if (material.isBlock()) {
                blocks.add(material);
            } else if (isTool(material)) {
                tools.add(material);
            } else if (material.isEdible()) {
                food.add(material);
            } else {
                misc.add(material);
            }
        }
    }

    private boolean isTool(Material material) {
        String materialName = material.name();
        return materialName.endsWith("_SWORD") ||
            materialName.endsWith("_PICKAXE") ||
            materialName.endsWith("_AXE") ||
            materialName.endsWith("_SHOVEL") ||
            materialName.endsWith("_HOE") ||
            materialName.contains("SHEARS") ||
            materialName.contains("FISHING_ROD") ||
            materialName.contains("FLINT_AND_STEEL") ||
            materialName.contains("BOW") ||
            materialName.contains("CROSSBOW") ||
            materialName.contains("TRIDENT") ||
            materialName.contains("COMPASS") ||
            materialName.contains("BRUSH");
    }

}
