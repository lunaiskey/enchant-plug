package me.lunaiskey.enchantplug.events;

import me.lunaiskey.enchantplug.EnchantPlug;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnvilOpen implements Listener {

    public ItemStack air = new ItemStack(Material.AIR);
    public ItemStack UNKNOWN_BARRIER = getItem(ChatColor.RED + "UNKNOWN",Material.BARRIER,null);
    public ItemStack ANVIL_COMBINE = getItem(ChatColor.GREEN + "Combine Items", Material.ANVIL, null);


    public ItemStack getItem(String displayName, Material material, List<String> lore) {
        ItemStack is = new ItemStack(material);
        ItemMeta isMeta = is.getItemMeta();
        if (displayName != null) {
            isMeta.setDisplayName(displayName);
        }
        if (lore != null) {
            isMeta.setLore(lore);
        }
        is.setItemMeta(isMeta);
        return is;
    }
    public ItemStack combineItems(ItemStack itemleft, ItemStack itemright) {
        ItemMeta itemleftmeta = itemleft.getItemMeta();
        ItemMeta itemrightmeta = itemright.getItemMeta();
        ItemStack result = new ItemStack(itemleft.getType());
        ItemMeta resultMeta = result.getItemMeta();


        if (itemleftmeta.hasDisplayName()) {
            resultMeta.setDisplayName(itemleftmeta.getDisplayName());
        }
        if (itemleftmeta.hasLore()) {
            resultMeta.setLore(itemleftmeta.getLore());
        }
        if (itemleftmeta.hasEnchants() && itemrightmeta.hasEnchants()) {


        } else if (itemleftmeta.hasEnchants() && !(itemrightmeta.hasEnchants())) {
            Map<Enchantment, Integer> ench = itemleftmeta.getEnchants();
            for (Map.Entry<Enchantment, Integer> entry : ench.entrySet()) {
                resultMeta.addEnchant(entry.getKey(),entry.getValue(),true);
            }
        } else if (itemrightmeta.hasEnchants() && !(itemleftmeta.hasEnchants())) {
            Map<Enchantment, Integer> ench = itemrightmeta.getEnchants();
            for (Map.Entry<Enchantment, Integer> entry : ench.entrySet()) {
                resultMeta.addEnchant(entry.getKey(), entry.getValue(), true);
            }
        }

        result.setItemMeta(resultMeta);
        return result;
    }
    public void onInvCloseItems(ItemStack dropItem, HumanEntity player) {
        if (dropItem != null && !(dropItem.getType().equals(Material.AIR))) {
            Map<Integer, ItemStack> map = player.getInventory().addItem(dropItem);
            for (ItemStack item : map.values()) {
                player.getWorld().dropItemNaturally(player.getLocation(), item);
            }
        }
    }

    @EventHandler
    public void onAnvilOpen(InventoryOpenEvent e) {
        HumanEntity player = e.getPlayer();
        Inventory anvilInv = Bukkit.createInventory(null,45,"Anvil");
            for (int i = 0; i < 45; i++) {
                if(i!=13&&i!=22&&i!=29&&i!=33){
                anvilInv.setItem(i,getItem(" ",Material.BLACK_STAINED_GLASS_PANE, null));
            anvilInv.setItem(22, ANVIL_COMBINE);
            anvilInv.setItem(13, UNKNOWN_BARRIER);
            }
        }
        if (e.getInventory().getType() == InventoryType.ANVIL) {
            e.setCancelled(true);
            player.openInventory(anvilInv);
        }
    }

    @EventHandler
    public void onAnvilClose(InventoryCloseEvent e) {
        HumanEntity player = e.getPlayer();
        InventoryView inv = e.getView();
        //player.sendMessage("InvClosed " + inv.getTitle());
        if (inv.getTitle().equals("Anvil")) {
            onInvCloseItems(inv.getItem(29), player);
            onInvCloseItems(inv.getItem(33), player);
            if (inv.getItem(13) != null && !(inv.getItem(13).getType().equals(Material.BARRIER))) {

            }

        }

    }

    @EventHandler
    public void onButtonClick(InventoryClickEvent e) {
        HumanEntity p = e.getWhoClicked();
        ItemStack clicked = e.getCurrentItem();
        InventoryView inv = e.getView();
        int slot = e.getSlot();
        ItemStack air = new ItemStack(Material.AIR);

        Inventory clickedinv = e.getClickedInventory();

        if (inv.getTitle().equals("Anvil")) {
            //p.sendMessage("Raw Slot: " + e.getRawSlot());
            if (clickedinv != null && clicked != null) {
                if (clickedinv.getType() == InventoryType.CHEST) {
                    if (slot != 29 && slot != 33) {
                        e.setCancelled(true);
                    }
                    if (slot == 22 && inv.getItem(29) != null && inv.getItem(33) != null) {
                        p.sendMessage("Items Combined.");
                        inv.setItem(13,combineItems(inv.getItem(29),inv.getItem(33)));
                        inv.setItem(29,air);
                        inv.setItem(33,air);
                        //Reminder: Setup check if items are valid.

                    }
                    if (slot == 13 && !(inv.getItem(13).getType().equals(Material.BARRIER))) {
                        e.setCancelled(false);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(EnchantPlug.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                inv.setItem(13, UNKNOWN_BARRIER);
                            }
                        }, 1L);
                    }
                }
            }
        }
    }
}
