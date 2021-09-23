package com.stuudent.BanItem.data;

import com.stuudent.BanItem.BanItemCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AllData {

    public static File configFile;
    public static File tempFile;
    public static YamlConfiguration cf;
    public static YamlConfiguration tempCf;

    static {
        configFile = new File("plugins/" + BanItemCore.instance.getName() + "/banItemData.yml");
        tempFile = new File("plugins/" + BanItemCore.instance.getName() + "/tempData.yml");
        cf = YamlConfiguration.loadConfiguration(configFile);
        tempCf = YamlConfiguration.loadConfiguration(tempFile);
    }

    public void save() {
        try {cf.save(configFile);} catch (IOException ignored) {}
    }

    public void tf_save() {
        try {tempCf.save(tempFile);} catch (IOException ignored) {}
    }

    /* 금지템 리스트 얻어오기 */

    public List<ItemStack> getRightClickItems() {
        ArrayList<ItemStack> itemList = new ArrayList<>();
        Map<String, Object> rawList = cf.getConfigurationSection("RIGHTCLICK_ITEMS").getValues(false);
        for(Map.Entry<String, Object> entry : rawList.entrySet()) {
            itemList.add((ItemStack) entry.getValue());
        }
        return itemList;
    }

    public List<ItemStack> getCraftItems() {
        ArrayList<ItemStack> itemList = new ArrayList<>();
        Map<String, Object> rawList = cf.getConfigurationSection("CRAFT_ITEMS").getValues(false);
        for(Map.Entry<String, Object> entry : rawList.entrySet()) {
            itemList.add((ItemStack) entry.getValue());
        }
        return itemList;
    }

    /* 금지템:[우클릭/조합] 인벤토리 만들기 */

    public Inventory getRightClickInventory(boolean isUserGUI, int page) {
        int startIndex = 36 * (page - 1);
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', BanItemCore.cf.getString("UseItemGUI", "")));
        for(int slot=0; slot < 36; slot++) {
            int index = startIndex + slot;
            ItemStack rcItem = getRightClickItem(index);
            inv.setItem(slot, rcItem);
        }
        if(!isUserGUI) {
            ItemStack preventClickItem = getPreventClickItem();
            for(int slot=36; slot < 54; slot++) {
                inv.setItem(slot, preventClickItem);
            }
        }
        inv.setItem(45, getPreviousItem());
        inv.setItem(49, getShowPageItem(page));
        inv.setItem(53, getNextItem());

        return inv;
    }

    public Inventory getCraftInventory(boolean isUserGUI, int page) {
        int startIndex = 36 * (page - 1);
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', BanItemCore.cf.getString("CraftItemGUI", "")));
        for(int slot=0; slot < 36; slot++) {
            int index = startIndex + slot;
            ItemStack craftItem = getCraftItem(index);
            inv.setItem(slot, craftItem);
        }
        if(!isUserGUI) {
            ItemStack preventClickItem = getPreventClickItem();
            for(int slot=36; slot < 54; slot++) {
                inv.setItem(slot, preventClickItem);
            }
        }
        inv.setItem(45, getPreviousItem());
        inv.setItem(49, getShowPageItem(page));
        inv.setItem(53, getNextItem());

        return inv;
    }

    public ItemStack getRightClickItem(int index) {
        return cf.getItemStack("RIGHTCLICK_ITEMS.INDEX_" + index, new ItemStack(Material.AIR));
    }

    public ItemStack getCraftItem(int index) {
        return cf.getItemStack("CRAFT_ITEMS.INDEX_" + index, new ItemStack(Material.AIR));
    }

    protected ItemStack getPreviousItem() {
        ItemStack defItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        ItemStack previousItem = BanItemCore.cf.getItemStack("PreviousItem.Type", defItem);
        ItemMeta itemMeta = previousItem.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', BanItemCore.cf.getString("PreviousItem.Name")));
        ArrayList<String> loreList = new ArrayList<>();
        for(String lore : BanItemCore.cf.getStringList("PreviousItem.Lore"))
            loreList.add(ChatColor.translateAlternateColorCodes('&', lore));
        itemMeta.setLore(loreList);
        previousItem.setItemMeta(itemMeta);
        return previousItem;
    }

    protected ItemStack getNextItem() {
        ItemStack defItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
        ItemStack nextItem = BanItemCore.cf.getItemStack("NextItem.Type", defItem);
        ItemMeta itemMeta = nextItem.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', BanItemCore.cf.getString("NextItem.Name")));
        ArrayList<String> loreList = new ArrayList<>();
        for(String lore : BanItemCore.cf.getStringList("NextItem.Lore"))
            loreList.add(ChatColor.translateAlternateColorCodes('&', lore));
        itemMeta.setLore(loreList);
        nextItem.setItemMeta(itemMeta);
        return nextItem;
    }

    protected ItemStack getPreventClickItem() {
        ItemStack defItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 8);
        ItemStack preventClickItem = BanItemCore.cf.getItemStack("PreventClickItem.Type", defItem);
        ItemMeta itemMeta = preventClickItem.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', BanItemCore.cf.getString("PreventClickItem.Name")));
        ArrayList<String> loreList = new ArrayList<>();
        for(String lore : BanItemCore.cf.getStringList("PreventClickItem.Lore"))
            loreList.add(ChatColor.translateAlternateColorCodes('&', lore));
        itemMeta.setLore(loreList);
        preventClickItem.setItemMeta(itemMeta);
        return preventClickItem;
    }

    protected ItemStack getShowPageItem(int page) {
        ItemStack defItem = new ItemStack(Material.SIGN);
        ItemStack showPageItem = BanItemCore.cf.getItemStack("ShowPageItem.Type", defItem);
        ItemMeta itemMeta = showPageItem.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', BanItemCore.cf.getString("ShowPageItem.Name")));
        ArrayList<String> loreList = new ArrayList<>();
        for(String lore : BanItemCore.cf.getStringList("ShowPageItem.Lore"))
            loreList.add(ChatColor.translateAlternateColorCodes('&', lore.replace("[PAGE]", String.valueOf(page))));
        itemMeta.setLore(loreList);
        showPageItem.setItemMeta(itemMeta);
        return showPageItem;
    }

    /* 금지템[설정] 임시데이터 관리 */

    public int getRightClickPage() {
        return tempCf.getInt("SETRC.PAGE", 1);
    }

    public int getCraftPage() {
        return tempCf.getInt("SETCRAFT.PAGE", 1);
    }

    public Player getRightClickPlayer() {
        try {
            UUID uuid = UUID.fromString(tempCf.getString("SETRC.PLAYER", ""));
            return Bukkit.getPlayer(uuid);
        } catch(IllegalArgumentException e) {
            return null;
        }
    }

    public Player getCraftPlayer() {
        try {
            UUID uuid = UUID.fromString(tempCf.getString("SETCRAFT.PLAYER", ""));
            return Bukkit.getPlayer(uuid);
        } catch(IllegalArgumentException e) {
            return null;
        }
    }

    public boolean getUserRightClickState(Player targetPlayer) {
        return tempCf.getBoolean("RIGHTCLICK." + targetPlayer.getUniqueId().toString() + ".STATE", false);
    }

    public boolean getUserCraftState(Player targetPlayer) {
        return tempCf.getBoolean("CRAFT." + targetPlayer.getUniqueId().toString() + ".STATE", false);
    }

    public int getUserRightClickPage(Player targetPlayer) {
        return tempCf.getInt("RIGHTCLICK." + targetPlayer.getUniqueId().toString() + ".PAGE", 1);
    }

    public int getUserCraftPage(Player targetPlayer) {
        return tempCf.getInt("CRAFT." + targetPlayer.getUniqueId().toString() + ".PAGE", 1);
    }

    public void setUserRightClickState(Player targetPlayer) {
        tempCf.set("RIGHTCLICK." + targetPlayer.getUniqueId().toString() + ".STATE", true);
    }

    public void setUserCraftState(Player targetPlayer) {
        tempCf.set("CRAFT." + targetPlayer.getUniqueId().toString() + ".STATE", true);
    }

    public void setUserRightClickPage(Player targetPlayer, int page) {
        tempCf.set("RIGHTCLICK." + targetPlayer.getUniqueId().toString() + ".PAGE", page);
    }

    public void setUserCraftPage(Player targetPlayer, int page) {
        tempCf.set("CRAFT." + targetPlayer.getUniqueId().toString() + ".PAGE", page);
    }

    public void removeUserRightClickTemp(Player targetPlayer) {
        tempCf.set("RIGHTCLICK." + targetPlayer.getUniqueId().toString() + ".STATE", null);
        tempCf.set("RIGHTCLICK." + targetPlayer.getUniqueId().toString() + ".PAGE", null);
    }

    public void removeUserCraftTemp(Player targetPlayer) {
        tempCf.set("CRAFT." + targetPlayer.getUniqueId().toString() + ".STATE", null);
        tempCf.set("CRAFT." + targetPlayer.getUniqueId().toString() + ".PAGE", null);

    }

    public void setRightClickPlayer(Player targetPlayer) {
        tempCf.set("SETRC.PLAYER", targetPlayer.getUniqueId().toString());
    }

    public void setCraftPlayer(Player targetPlayer) {
        tempCf.set("SETCRAFT.PLAYER", targetPlayer.getUniqueId().toString());
    }

    public void removeRightClickTemp() {
        tempCf.set("SETRC", null);
    }

    public void removeCraftTemp() {
        tempCf.set("SETCRAFT", null);
    }

    public void setRightClickPage(int page) {
        tempCf.set("SETRC.PAGE", page);
    }

    public void setCraftPage(int page) {
        tempCf.set("SETCRAFT.PAGE", page);
    }

    public void setRightClickItem(ItemStack targetItem, int index) {
        cf.set("RIGHTCLICK_ITEMS.INDEX_" + index, targetItem);
    }

    public void setCraftItem(ItemStack targetItem, int index) {
        cf.set("CRAFT_ITEMS.INDEX_" + index, targetItem);
    }

    public void delRightClickItem(int index) {
        cf.set("RIGHTCLICK_ITEMS.INDEX_" + index, null);
    }

    public void delCraftItem(int index) {
        cf.set("CRAFT_ITEMS.INDEX_" + index, null);
    }



}
