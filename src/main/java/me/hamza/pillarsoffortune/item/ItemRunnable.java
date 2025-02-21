package me.hamza.pillarsoffortune.item;

import me.hamza.pillarsoffortune.POF;
import me.hamza.pillarsoffortune.game.Game;
import me.hamza.pillarsoffortune.game.GameState;
import me.hamza.pillarsoffortune.utils.CC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
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

        Game activeGame = POF.getInstance().getGameManager().getActiveGame();

        if (activeGame == null || activeGame.getState() != GameState.PLAYING) {
            return;
        }

        if (ticks >= intervalTicks) {
            for (Player player : activeGame.getGamePlayers()) {
                Material selectedMaterial = POF.getInstance().getItemRandomizer().getItem();
                if (selectedMaterial != null) {
                    ItemStack item = new ItemStack(selectedMaterial);
                    PlayerInventory inventory = player.getInventory();

                    if (inventory.firstEmpty() != -1) {
                        inventory.addItem(item);
                        player.sendMessage(CC.color("&aYou received the &c" + selectedMaterial.name() + " &aitem!"));
                    } else {
                        player.getWorld().dropItem(player.getLocation(), item);
                        player.sendMessage(CC.color("&eYour received the &c" + selectedMaterial.name() + " &eitem but your inventory was full!"));
                    }
                }
            }

            ticks = 0;
        }

        ticks++;
    }
}