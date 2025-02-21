package me.hamza.pillarsoffortune.item;

import me.hamza.pillarsoffortune.POF;
import me.hamza.pillarsoffortune.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Hammzar
 * @since 20/02/2025
 */

public class ItemRunnable extends BukkitRunnable {

    private int ticks = 0;

    @Override
    public void run() {
        int intervalSeconds = 10;
        int intervalTicks = intervalSeconds * 20;

        if (ticks >= intervalTicks) {
            Material selectedMaterial = POF.getInstance().getItemRandomizer().getItem();
            if (selectedMaterial != null) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.getInventory().addItem(new ItemStack(selectedMaterial));
                    player.sendMessage(CC.color("&aYou received the &c" + selectedMaterial.name() + " &aitem!"));
                }
            }

            ticks = 0;
        }

        ticks++;
    }

}
