package me.lunaiskey.enchantplug;

import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.EnumSet;

public final class EnchantPlug extends JavaPlugin {

    private static EnchantPlug instance;
    public static EnchantPlug getInstance() {
        return instance;
    }


    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("anvil").setExecutor(new Anvil());
        getServer().getPluginManager().registerEvents(new ETableLogic(), this);
        instance = this;
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
