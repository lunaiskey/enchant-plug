package me.lunaiskey.enchantplug.core.objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
    private ItemStack enchantPlaceholder = createItem(Material.GRAY_DYE, ChatColor.RED + "Enchant Item");
    private ItemStack enchantTable = createItem(Material.ENCHANTING_TABLE, ChatColor.LIGHT_PURPLE + "Enchant Item");
    private ItemStack enchantBook = createItem(Material.ENCHANTED_BOOK, "Placeholder");

    public Inventory customEnchantTable() {
        Inventory inv = Bukkit.createInventory(null, 54, menuDisplayName);
        for (int i = 0; i < 54; i++) {
            inv.setItem(i, menuFiller);
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
        for (int i = 0; i < 15; i++) {
            if (i == 7) {
                inv.setItem(getSlotID(i), getEnchantPlaceholder());
            } else {
                inv.setItem(getSlotID(i), createItem(Material.BLACK_STAINED_GLASS_PANE, " "));
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
    public ItemStack createItem(Material mat, String displayname, List<String> lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        if (!displayname.equals("null")) {
            meta.setDisplayName(displayname);
        }
        if (!lore.isEmpty()) {
            meta.setLore(lore);
        }
        item.setItemMeta(meta);
        return item;
    }

    public int getSlotID(Integer slot) throws IndexOutOfBoundsException{
        if (slot >= 0 && slot <= 4) {
            return slot + 12;
        }
        if (slot >= 5 && slot <= 9) {
            return slot + 16;
        }
        if (slot >= 10 && slot <= 14) {
            return slot + 20;
        }
        if (slot >= 15) {
            throw new IndexOutOfBoundsException("Higher the 15");
        }
        return -1;
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
