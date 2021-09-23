package com.stuudent.BanItem.listeners;

import com.stuudent.BanItem.BanItemAPI;
import com.stuudent.BanItem.data.AllData;
import com.stuudent.BanItem.data.ItemData;
import com.stuudent.BanItem.data.PlayerData;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class AdminListeners implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        PlayerData playerData = BanItemAPI.getPlayer(player);
        AllData allData = BanItemAPI.getData();
        if(playerData.isSettingRightClick()) {
            if (e.getRawSlot() >= 36 && e.getRawSlot() <= 53) {
                e.setCancelled(true);
                int page = allData.getRightClickPage();
                if (e.getRawSlot() == 45 && page != 1) {
                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                    player.openInventory(allData.getRightClickInventory(false, page - 1));
                    playerData.setRightClickPlayer();
                    allData.setRightClickPage(page - 1);
                    return;
                }
                if (e.getRawSlot() == 53) {
                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                    player.openInventory(allData.getRightClickInventory(false, page + 1));
                    playerData.setRightClickPlayer();
                    allData.setRightClickPage(page + 1);
                    return;
                }
                return;
            }
        }
        if(playerData.isSettingCraft()) {
            if(e.getRawSlot() >= 36 && e.getRawSlot() <= 53) {
                e.setCancelled(true);
                int page = allData.getCraftPage();
                if(e.getRawSlot() == 45 && page != 1) {
                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                    player.openInventory(allData.getCraftInventory(false, page - 1));
                    playerData.setCraftPlayer();
                    allData.setCraftPage(page - 1);
                    return;
                }
                if(e.getRawSlot() == 53) {
                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                    player.openInventory(allData.getCraftInventory(false, page + 1));
                    playerData.setCraftPlayer();
                    allData.setCraftPage(page + 1);
                }
            }

        }

    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        PlayerData playerData = BanItemAPI.getPlayer(player);
        AllData allData = BanItemAPI.getData();
        if(playerData.isSettingRightClick()) {
            int startIndex = 36 * (allData.getRightClickPage() - 1);
            Inventory inv = e.getInventory();
            for(int slot=0; slot < 36; slot++) {
                int index = startIndex + slot;
                if(inv.getItem(slot) != null) {
                    ItemData itemData = BanItemAPI.getItem(inv.getItem(slot));
                    itemData.setRightClickItem(index);
                } else {
                    allData.delRightClickItem(index);
                }
            }
            allData.save();
            allData.removeRightClickTemp();
            return;
        }
        if(playerData.isSettingCraft()) {
            int startIndex = 36 * (allData.getCraftPage() - 1);
            Inventory inv = e.getInventory();
            for(int slot=0; slot < 36; slot++) {
                int index = startIndex + slot;
                if(inv.getItem(slot) != null) {
                    ItemData itemData = BanItemAPI.getItem(inv.getItem(slot));
                    itemData.setCraftItem(index);
                } else {
                    allData.delCraftItem(index);
                }
            }
            allData.save();
            allData.removeCraftTemp();
        }
    }
}
