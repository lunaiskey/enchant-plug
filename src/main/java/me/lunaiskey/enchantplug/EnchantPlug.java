package me.lunaiskey.enchantplug;

import me.lunaiskey.enchantplug.events.AnvilOpen;
import org.bukkit.plugin.java.JavaPlugin;

public final class EnchantPlug extends JavaPlugin {

    private static EnchantPlug instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        getServer().getPluginManager().registerEvents(new AnvilOpen(), this);
    }
    public static EnchantPlug getInstance(){
        return instance;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
