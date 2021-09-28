package me.lunaiskey.enchantplug;

import me.lunaiskey.enchantplug.core.objects.EnchantTable;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ETableLogic implements Listener {
    EnchantTable etable = new EnchantTable();


    @EventHandler
    public void onETableOpen(InventoryOpenEvent e) {
        HumanEntity p = e.getPlayer();

        if (e.getInventory().getType() == InventoryType.ENCHANTING) {
            e.setCancelled(true);
            p.openInventory(etable.customEnchantTable());
        }

    }

    @EventHandler
    public void onETableClose(InventoryCloseEvent e) {
        HumanEntity p = e.getPlayer();
        Inventory inv = e.getInventory();
        if (inv.getLocation() == null && inv.getItem(19) != null && inv.getItem(19).getType() != Material.AIR && e.getView().getTitle().equalsIgnoreCase(etable.getMenuDisplayName())) {
            Item item = p.getWorld().dropItem(p.getLocation().add(0,1,0), inv.getItem(19));
            item.setPickupDelay(40);
            item.setVelocity(p.getLocation().getDirection().multiply(0.5));
        }
    }

    @EventHandler
    public void onETableClick(InventoryClickEvent e) {
        HumanEntity p = e.getWhoClicked();
        if (e.getClickedInventory() != null) {
            Inventory inv = e.getClickedInventory();
            if (e.getSlot() != 19 && inv.getLocation() == null && e.getView().getTitle().equalsIgnoreCase(etable.getMenuDisplayName())) {
                e.setCancelled(true);
            }

            if (e.getSlot() == 19 && inv.getLocation() == null && e.getView().getTitle().equalsIgnoreCase(etable.getMenuDisplayName())) {
                if (e.getAction() == InventoryAction.PICKUP_ALL || e.getAction() == InventoryAction.PLACE_ALL || e.getAction() == InventoryAction.SWAP_WITH_CURSOR) {
                    if (e.getAction() == InventoryAction.PLACE_ALL || e.getAction() == InventoryAction.SWAP_WITH_CURSOR) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                //p.sendMessage(e.getInventory().getItem(19).toString());

                                for (int i = 0;i < 15;i++) {
                              //for (int i = 0;i < getEnchants(e.getInventory().getItem(19).getType()).length;i++) {
                                    if (i < getEnchants(e.getInventory().getItem(19).getType()).length) {
                                        ItemStack ebook = etable.getEnchantBook();
                                        ItemMeta emeta = ebook.getItemMeta();
                                        Enchantment enchant = getEnchants(e.getInventory().getItem(19).getType())[i];
                                        emeta.setDisplayName(ChatColor.GREEN + getEnchantFormatted(enchant));
                                        //List<String> lore = new ArrayList<String>();
                                        //lore.add(enchant.getKey().getKey());
                                        //emeta.setLore(lore);
                                        ebook.setItemMeta(emeta);
                                        e.getInventory().setItem(getSlotID(i), ebook);
                                    } else if (getEnchants(e.getInventory().getItem(19).getType()).length == 0){
                                        e.getInventory().setItem(getSlotID(i),etable.createItem(Material.IRON_BARS,ChatColor.RED + "Invalid Item"));
                                    } else if (i >= getEnchants(e.getInventory().getItem(19).getType()).length){
                                        e.getInventory().setItem(getSlotID(i),etable.createItem(Material.WHITE_STAINED_GLASS_PANE," "));
                                    }
                                }
                            }
                        }.runTaskLater(EnchantPlug.getInstance(),1);
                    } else { //PICKUP_ALL
                        for (int i = 0; i < 54; i++) {
                            if ((i >= 12 && i <= 16) || (i >= 21 && i <= 25) || (i >= 30 && i <= 34)) {
                                inv.setItem(i, etable.getEnchantPlaceholder());
                            }
                        }
                    }
                } else {
                    e.setCancelled(true);
                }
            }
            if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                e.setCancelled(true);
            }
        }
    }

    private Enchantment[] getEnchants(Material mat) {
        String name = mat.name().toLowerCase();
        if (name.contains("_sword")) {
            return new Enchantment[]{
                    Enchantment.DAMAGE_ALL,
                    Enchantment.DAMAGE_ARTHROPODS,
                    Enchantment.DAMAGE_UNDEAD,
                    Enchantment.FIRE_ASPECT,
                    Enchantment.DURABILITY,
                    Enchantment.LOOT_BONUS_MOBS,
                    Enchantment.KNOCKBACK,
                    Enchantment.SWEEPING_EDGE,
                    Enchantment.MENDING
            };
        } else if (name.contains("_pickaxe") || name.contains("_shovel") || name.contains("_hoe")) {
            return new Enchantment[]{
                    Enchantment.DIG_SPEED,
                    Enchantment.LOOT_BONUS_BLOCKS,
                    Enchantment.SILK_TOUCH,
                    Enchantment.DURABILITY,
                    Enchantment.MENDING
            };
        } else if (name.contains("_axe")) {
            return new Enchantment[]{
                    Enchantment.DAMAGE_ALL,
                    Enchantment.DAMAGE_ARTHROPODS,
                    Enchantment.DAMAGE_UNDEAD,
                    Enchantment.DIG_SPEED,
                    Enchantment.SILK_TOUCH,
                    Enchantment.LOOT_BONUS_BLOCKS,
                    Enchantment.DURABILITY,
                    Enchantment.MENDING
            };
        } else if (name.contains("_helmet")) {
            return new Enchantment[]{
                    Enchantment.PROTECTION_ENVIRONMENTAL,
                    Enchantment.PROTECTION_PROJECTILE,
                    Enchantment.PROTECTION_FIRE,
                    Enchantment.PROTECTION_EXPLOSIONS,
                    Enchantment.OXYGEN,
                    Enchantment.WATER_WORKER,
                    Enchantment.THORNS,
                    Enchantment.DURABILITY,
                    Enchantment.MENDING
            };
        } else if (name.contains("_chestplate") || name.contains("_leggings") ) {
            return new Enchantment[]{
                    Enchantment.PROTECTION_ENVIRONMENTAL,
                    Enchantment.PROTECTION_PROJECTILE,
                    Enchantment.PROTECTION_FIRE,
                    Enchantment.PROTECTION_EXPLOSIONS,
                    Enchantment.THORNS,
                    Enchantment.DURABILITY,
                    Enchantment.MENDING
            };
        } else if (name.contains("_boots")) {
            return new Enchantment[]{
                    Enchantment.PROTECTION_ENVIRONMENTAL,
                    Enchantment.PROTECTION_PROJECTILE,
                    Enchantment.PROTECTION_FIRE,
                    Enchantment.PROTECTION_EXPLOSIONS,
                    Enchantment.PROTECTION_FALL,
                    Enchantment.SOUL_SPEED,
                    Enchantment.DEPTH_STRIDER,
                    Enchantment.FROST_WALKER,
                    Enchantment.THORNS,
                    Enchantment.DURABILITY,
                    Enchantment.MENDING
            };
        } else if (name.contains("bow")) {
            return new Enchantment[]{
                    Enchantment.ARROW_DAMAGE,
                    Enchantment.ARROW_KNOCKBACK,
                    Enchantment.ARROW_FIRE,
                    Enchantment.ARROW_INFINITE,
                    Enchantment.DURABILITY,
                    Enchantment.MENDING
            };
        } else if (name.contains("fishing_rod")) {
            return new Enchantment[]{
                    Enchantment.LURE,
                    Enchantment.LUCK,
                    Enchantment.DURABILITY,
                    Enchantment.MENDING
            };
        } else if (name.contains("trident")) {
            return new Enchantment[]{
                    Enchantment.RIPTIDE,
                    Enchantment.LOYALTY,
                    Enchantment.CHANNELING,
                    Enchantment.IMPALING,
                    Enchantment.DURABILITY,
                    Enchantment.MENDING
            };
        } else if (name.contains("crossbow")) {
            return new Enchantment[]{
                    Enchantment.PIERCING,
                    Enchantment.MULTISHOT,
                    Enchantment.QUICK_CHARGE,
                    Enchantment.DURABILITY,
                    Enchantment.MENDING
            };
        } else if (name.contains("shears")) {
            return new Enchantment[]{
                    Enchantment.DIG_SPEED,
                    Enchantment.SILK_TOUCH,
                    Enchantment.DURABILITY,
                    Enchantment.MENDING
            };
        } else if (name.contains("shield") || name.contains("elytra") || name.contains("flint_and_steel") || name.contains("on_a_stick")) {
            return new Enchantment[]{
                    Enchantment.DURABILITY,
                    Enchantment.MENDING
            };
        }
        return new Enchantment[]{};
    }

    private int getSlotID(Integer slot) throws IndexOutOfBoundsException{
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
    private String getEnchantFormatted(Enchantment enchant) {
        String ench = enchant.getKey().getKey();
        if (ench.equals("sweeping")) {
            ench = "sweeping_edge";
        }
        ench = ench.replace("_"," ");
        ench = WordUtils.capitalize(ench);
        return ench;


    }
}
