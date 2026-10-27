package org.strayzii.strayTotems;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.strayzii.strayTotems.Listeners.CommandsListener;
import org.strayzii.strayTotems.Listeners.InventoryListener;
import org.strayzii.strayTotems.Listeners.PlayerJoinListener;

public final class StrayTotems extends JavaPlugin {
    private Utils utils = new Utils(this);

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new InventoryListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);

        CommandsListener instance = new CommandsListener(this);
        getCommand("straytotems").setExecutor(instance);
        getCommand("straytotems").setTabCompleter(instance);
    }

    public Utils getUtils() {
        return utils;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
