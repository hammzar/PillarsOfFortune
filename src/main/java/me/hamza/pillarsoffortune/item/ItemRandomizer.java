package me.hamza.pillarsoffortune.item;

import lombok.Getter;
import me.hamza.pillarsoffortune.Mortal;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Hammzar
 * @since 21/02/2025
 */

@Getter
public class ItemRandomizer {

    private final ItemOrganizer itemOrganizer = Mortal.getInstance().getItemOrganizer();

    public Material getItem() {
        double chance = ThreadLocalRandom.current().nextDouble(100);
        int chanceGroup;

        if (chance < 60) {
            chanceGroup = 0;
        } else if (chance < 80) {
            chanceGroup = 1;
        } else if (chance < 90) {
            chanceGroup = 2;
        } else {
            chanceGroup = 3;
        }

        List<Material> category;
        switch (chanceGroup) {
            case 0:
                category = new ArrayList<>(itemOrganizer.getBlocks());
                break;
            case 1:
                category = new ArrayList<>(itemOrganizer.getTools());
                break;
            case 2:
                category = new ArrayList<>(itemOrganizer.getFood());
                break;
            case 3:
                category = new ArrayList<>(itemOrganizer.getMisc());
                break;
            default:
                category = new ArrayList<>();
        }

        return category.isEmpty() ? null : category.get(ThreadLocalRandom.current().nextInt(category.size()));
    }

}
