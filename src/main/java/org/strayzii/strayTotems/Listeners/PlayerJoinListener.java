package org.strayzii.strayTotems.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;
import org.strayzii.strayTotems.StrayTotems;

public class PlayerJoinListener implements Listener {
    private final StrayTotems plugin;

    public PlayerJoinListener(@NotNull StrayTotems plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        plugin.getUtils().checkInventory(e.getPlayer());
    }
}
