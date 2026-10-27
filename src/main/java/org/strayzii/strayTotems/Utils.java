package org.strayzii.strayTotems;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Utils {
    private final StrayTotems plugin;

    public Utils(StrayTotems plugin) {
        this.plugin = plugin;
    }

    public void checkEveryone() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            checkInventory(player);
        }
    }

    public void checkInventory(@NotNull Player player) {
        Inventory inv = player.getInventory();
        if (inv == null) return;

        int total = 0;

        for (ItemStack item : inv.getContents()) {
            if (item != null && item.getType() == Material.TOTEM_OF_UNDYING) {
                total += item.getAmount();
            }
        }

        int max = plugin.getConfig().getInt("max-totems", -1);
        if (max <= -1)
            return;

        if (total > max) {
            for (int slot = 0; slot < inv.getSize(); slot++) {
                if (total <= max) break;
                ItemStack item = inv.getItem(slot);

                if (item == null || item.getType() != Material.TOTEM_OF_UNDYING) {
                    continue;
                }

                inv.setItem(slot, null);
                player.getWorld().dropItemNaturally(player.getLocation(), item);

                total -= item.getAmount();
            }

            String prefix = plugin.getConfig().getString("messages.prefix", "§x§4§4§9§8§D§Bᴛᴏᴛᴇᴍs &8»&r");
            String msg = plugin.getConfig().getString("messages.on-max-totems", "$prefix §x§B§5§C§4§E§0В инвентаре может находится максимум $amount тотемов.");

            msg = msg.replace("$prefix", prefix);
            msg = msg.replace("$amount", String.valueOf(max));

            player.sendMessage(
                    ChatColor.translateAlternateColorCodes('&', msg)
            );
        }
    }
}
