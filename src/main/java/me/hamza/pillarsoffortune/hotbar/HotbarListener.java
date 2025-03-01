package me.hamza.pillarsoffortune.hotbar;

import me.hamza.pillarsoffortune.Mortal;
import me.hamza.pillarsoffortune.player.PlayerData;
import me.hamza.pillarsoffortune.player.PlayerState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

/**
 * @author Hammzar
 * @since 01/03/2025
 */

public class HotbarListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack itemInHand = player.getItemInHand();

        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        PlayerData playerData = Mortal.getInstance().getPlayerHandler().getPlayer(player.getUniqueId());

        if (playerData.getState() != PlayerState.LOBBY) {
            return;
        }

        if (!itemInHand.hasItemMeta() || !Objects.requireNonNull(itemInHand.getItemMeta()).hasDisplayName()) {
            return;
        }

        HotbarItem hotbarItem = Mortal.getInstance().getHotbarHandler().getHotbarItem(itemInHand);
        if (hotbarItem == null) {
            return;
        }

        String command = hotbarItem.getCommand();
        if (command != null && !command.isEmpty()) {
            player.performCommand(command);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        PlayerData playerData = Mortal.getInstance().getPlayerHandler().getPlayer(player.getUniqueId());

        if (playerData.getState() == PlayerState.LOBBY) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getEntity();
        PlayerData playerData = Mortal.getInstance().getPlayerHandler().getPlayer(player.getUniqueId());

        if (playerData.getState() == PlayerState.LOBBY) {
            e.setCancelled(true);
        }
    }
}
