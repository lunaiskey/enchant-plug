package me.lunaiskey.enchantplug;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

public class Anvil implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            OpenAnvil(p);
        }
        return true;
    }

    public void OpenAnvil(Player p) {
        Inventory anvil = Bukkit.createInventory(null, 54,"Anvil");
        for (int i = 0; i < 54; i ++) {
            anvil.setItem(i,CreateItem(Material.BLACK_STAINED_GLASS_PANE, "&r"));
        }

        p.openInventory(anvil);
    }

    public ItemStack CreateItem(Material material, String DisplayName) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemmetadata = item.getItemMeta();
        if (DisplayName != null) {
            itemmetadata.setDisplayName(ChatColor.translateAlternateColorCodes('&',DisplayName));
        }
        item.setItemMeta(itemmetadata);
        return item;
    }
}
