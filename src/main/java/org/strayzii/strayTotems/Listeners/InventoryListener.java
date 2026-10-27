package org.strayzii.strayTotems.Listeners;

import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.strayzii.strayTotems.StrayTotems;

public class InventoryListener implements Listener {
    private final StrayTotems plugin;

    public InventoryListener(StrayTotems plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryChange(PlayerInventorySlotChangeEvent e) {
        ItemStack newItem = e.getNewItemStack();

        if (newItem.getType() == Material.TOTEM_OF_UNDYING) {
            plugin.getUtils().checkInventory(e.getPlayer());
        }
    }
}
