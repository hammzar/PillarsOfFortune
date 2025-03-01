package me.hamza.pillarsoffortune.hotbar;


import lombok.Getter;
import me.hamza.pillarsoffortune.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Hammzar
 * @since 01/03/2025
 */

@Getter
public class HotbarHandler {

    private final List<HotbarItem> hotbarItemList = new ArrayList<>();

    public void init() {
        for (HotbarItems hotbarItems : HotbarItems.values()) {
            hotbarItemList.add(new HotbarItem(
                            hotbarItems.getName(),
                            hotbarItems.getMaterial(),
                            hotbarItems.getDurability(),
                            hotbarItems.getSlot(),
                            hotbarItems.getLore(),
                            hotbarItems.getCommand())
            );
        }
    }

    public void applyItems(Player player) {
        for (HotbarItem hotbarItem : hotbarItemList) {
            ItemStack item = new ItemStack(hotbarItem.getMaterial(), 1, (short) hotbarItem.getDurability());
            ItemMeta meta = item.getItemMeta();

            if (meta != null) {
                meta.setDisplayName(CC.color(hotbarItem.getName()));
                meta.setLore(CC.color(hotbarItem.getLore()));
                item.setItemMeta(meta);
            }

            player.getInventory().setItem(hotbarItem.getSlot(), item);
        }
    }

    public HotbarItem getHotbarItem(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta() || !Objects.requireNonNull(itemStack.getItemMeta()).hasDisplayName()) {
            return null;
        }

        String displayName = itemStack.getItemMeta().getDisplayName();

        for (HotbarItem hotbarItem : hotbarItemList) {
            if (CC.color(hotbarItem.getName()).equalsIgnoreCase(displayName)) {
                return hotbarItem;
            }
        }
        return null;
    }


}
