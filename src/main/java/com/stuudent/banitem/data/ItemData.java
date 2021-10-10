package com.stuudent.banitem.data;

import com.stuudent.banitem.BanItemCore;
import com.stuudent.banitem.enums.BannedType;
import com.stuudent.banitem.enums.BlockColor;
import com.stuudent.banitem.interfaces.BanItem;
import dev.dbassett.skullcreator.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemData implements BanItem {

    public static File itemDataFile;
    public static YamlConfiguration itemData;
    public ItemStack banItem;

    static {
        itemDataFile = new File("plugins/" + BanItemCore.instance.getName() + "/itemData.yml");
        itemData = YamlConfiguration.loadConfiguration(itemDataFile);
    }

    public ItemData(ItemStack banItem) {
        this.banItem = banItem;
    }

    public static void save() {
        try {
            itemData.save(itemDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setItemBanned(BannedType bannedType, int index) {
        itemData.set(bannedType.name() + ".INDEX_" + index, this.banItem);
    }

    @Override
    public boolean isItemBanned(BannedType bannedType) {
        for(ItemStack itemStack : getBannedItems(bannedType))
            if(this.banItem.isSimilar(itemStack)) return true;
        return false;
    }

    public static void resetBannedItem(BannedType bannedType, int index) {
        itemData.set(bannedType.name() + ".INDEX_" + index, null);
    }

    public static ItemStack getBannedItem(BannedType bannedType, int index) {
        return itemData.getItemStack(bannedType.name() + ".INDEX_" + index, null);
    }

    public static List<ItemStack> getBannedItems(BannedType bannedType) {
        ArrayList<ItemStack> itemList = new ArrayList<>();
        Map<String, Object> rawList = itemData.getConfigurationSection(bannedType.name()).getValues(false);
        for(Map.Entry<String, Object> entry : rawList.entrySet()) {
            itemList.add((ItemStack) entry.getValue());
        }
        return itemList;
    }

    public static Inventory getBannedItemsGUI(BannedType bannedType, boolean isSettingGUI, int page) {
        Inventory inventory = null;
        if(bannedType.equals(BannedType.RIGHT_CLICK)) {
            inventory = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&',
                    BanItemCore.cf.getString("RightClickBannedItemsGUI")));
        } else if(bannedType.equals(BannedType.CRAFT)) {
            inventory = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&',
                    BanItemCore.cf.getString("CraftBannedItemsGUI")));
        }
        int startIndex = 36 * (page - 1);
        for(int slot=0; slot < 36; slot++) {
            int index = startIndex + slot;
            if(getBannedItem(bannedType, index) == null) continue;
            ItemStack bannedItem = getBannedItem(bannedType, index);
            inventory.setItem(slot, bannedItem);
        }
        if(isSettingGUI) {
            ItemStack preventClickItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, BlockColor.LIGHT_GRAY.getData());
            ItemMeta preventClickItemMeta = preventClickItem.getItemMeta(); List<String> preventClickItemLore = new ArrayList<>();
            preventClickItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', BanItemCore.cf.getString("PreventClickItem.Name")));
            for(String lore : BanItemCore.cf.getStringList("PreventClickItem.Lore"))
                preventClickItemLore.add(ChatColor.translateAlternateColorCodes('&', lore));
            preventClickItemMeta.setLore(preventClickItemLore);
            preventClickItem.setItemMeta(preventClickItemMeta);
            for(int slot=36; slot < 54; slot++) {
                inventory.setItem(slot, preventClickItem);
            }
        }
        ItemStack previous = SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/8652e2b936ca8026bd28651d7c9f2819d2e923697734d18dfdb13550f8fdad5f");
        ItemMeta previousMeta = previous.getItemMeta(); List<String> previousLore = new ArrayList<>();
        previousMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', BanItemCore.cf.getString("PreviousItem.Name")));
        for(String lore : BanItemCore.cf.getStringList("PreviousItem.Lore"))
            previousLore.add(ChatColor.translateAlternateColorCodes('&', lore));
        previousMeta.setLore(previousLore);
        previous.setItemMeta(previousMeta);

        ItemStack next = SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/2a3b8f681daad8bf436cae8da3fe8131f62a162ab81af639c3e0644aa6abac2f");
        ItemMeta nextMeta = next.getItemMeta(); List<String> nextLore = new ArrayList<>();
        nextMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', BanItemCore.cf.getString("NextItem.Name")));
        for(String lore : BanItemCore.cf.getStringList("NextItem.Lore"))
            nextLore.add(ChatColor.translateAlternateColorCodes('&', lore));
        nextMeta.setLore(nextLore);
        next.setItemMeta(nextMeta);

        ItemStack pageItem = new ItemStack(Material.SIGN);
        ItemMeta pageItemMeta = pageItem.getItemMeta(); List<String> pageItemLore = new ArrayList<>();
        pageItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', BanItemCore.cf.getString("ShowPageItem.Name")));
        for(String lore : BanItemCore.cf.getStringList("ShowPageItem.Lore"))
            pageItemLore.add(ChatColor.translateAlternateColorCodes('&', lore.replace("%page%", String.valueOf(page))));
        pageItemMeta.setLore(pageItemLore);
        pageItem.setItemMeta(pageItemMeta);
        inventory.setItem(45, previous); inventory.setItem(49, pageItem); inventory.setItem(53, next);
        return inventory;
    }

}
