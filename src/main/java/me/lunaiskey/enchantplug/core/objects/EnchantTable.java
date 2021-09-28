package me.lunaiskey.enchantplug.core.objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EnchantTable {

    private String menuDisplayName = "Enchant Item";
    private List<String> lore = new ArrayList<String>();

    private ItemStack menuFiller = createItem(Material.BLACK_STAINED_GLASS_PANE," ");
    private ItemStack closeBarrier = createItem(Material.BARRIER, ChatColor.RED + "Close");
    private ItemStack enchantPlaceholder = createItem(Material.GRAY_DYE, "Unknown");
    private ItemStack enchantTable = createItem(Material.ENCHANTING_TABLE, ChatColor.LIGHT_PURPLE + "Place Item Above");
    private ItemStack enchantBook = createItem(Material.ENCHANTED_BOOK, "Placeholder");

    public Inventory customEnchantTable() {
        Inventory inv = Bukkit.createInventory(null, 54, menuDisplayName);
        for (int i = 0; i < 54; i++) {
            inv.setItem(i, menuFiller);
            if ((i >= 12 && i <= 16) || (i >= 21 && i <= 25) || (i >= 30 && i <= 34)) {
                inv.setItem(i,enchantPlaceholder);
            }
            if (i == 19) {
                inv.setItem(i,new ItemStack(Material.AIR));
            }
            if (i == 28) {
                inv.setItem(i,enchantTable);
            }
            if (i == 49) {
                inv.setItem(i,closeBarrier);
            }
        }
        return inv;
    }
    public ItemStack createItem(Material mat, String displayname) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        if (!displayname.equals("null")) {
            meta.setDisplayName(displayname);
        }
        item.setItemMeta(meta);
        return item;
    }

    public String getMenuDisplayName() {
        return menuDisplayName;
    }

    public ItemStack getEnchantPlaceholder() {
        return enchantPlaceholder;
    }

    public ItemStack getEnchantBook() {
        return enchantBook;
    }
}
