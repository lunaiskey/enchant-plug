package me.lunaiskey.enchantplug;

import org.bukkit.plugin.java.JavaPlugin;


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
