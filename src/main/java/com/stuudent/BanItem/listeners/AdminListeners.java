package com.stuudent.BanItem.listeners;

import com.stuudent.BanItem.BanItemAPI;
import com.stuudent.BanItem.data.BIData;
import com.stuudent.BanItem.data.BIItem;
import com.stuudent.BanItem.data.BIPlayer;
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
        BIPlayer biPlayer = BanItemAPI.getPlayer(player);
        BIData biData = BanItemAPI.getData();
        if(biPlayer.isSettingRightClick()) {
            if (e.getRawSlot() >= 36 && e.getRawSlot() <= 53) {
                e.setCancelled(true);
                int page = biData.getRightClickPage();
                if (e.getRawSlot() == 45 && page != 1) {
                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                    player.openInventory(biData.getRightClickInventory(false, page - 1));
                    biPlayer.setRightClickPlayer();
                    biData.setRightClickPage(page - 1);
                    return;
                }
                if (e.getRawSlot() == 53) {
                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                    player.openInventory(biData.getRightClickInventory(false, page + 1));
                    biPlayer.setRightClickPlayer();
                    biData.setRightClickPage(page + 1);
                    return;
                }
                return;
            }
        }
        if(biPlayer.isSettingCraft()) {
            if(e.getRawSlot() >= 36 && e.getRawSlot() <= 53) {
                e.setCancelled(true);
                int page = biData.getCraftPage();
                if(e.getRawSlot() == 45 && page != 1) {
                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                    player.openInventory(biData.getCraftInventory(false, page - 1));
                    biPlayer.setCraftPlayer();
                    biData.setCraftPage(page - 1);
                    return;
                }
                if(e.getRawSlot() == 53) {
                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                    player.openInventory(biData.getCraftInventory(false, page + 1));
                    biPlayer.setCraftPlayer();
                    biData.setCraftPage(page + 1);
                }
            }

        }

    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        BIPlayer biPlayer = BanItemAPI.getPlayer(player);
        BIData biData = BanItemAPI.getData();
        if(biPlayer.isSettingRightClick()) {
            int startIndex = 36 * (biData.getRightClickPage() - 1);
            Inventory inv = e.getInventory();
            for(int slot=0; slot < 36; slot++) {
                int index = startIndex + slot;
                if(inv.getItem(slot) != null) {
                    BIItem biItem = BanItemAPI.getItem(inv.getItem(slot));
                    biItem.setRightClickItem(index);
                } else {
                    biData.delRightClickItem(index);
                }
            }
            biData.save();
            biData.removeRightClickTemp();
            return;
        }
        if(biPlayer.isSettingCraft()) {
            int startIndex = 36 * (biData.getCraftPage() - 1);
            Inventory inv = e.getInventory();
            for(int slot=0; slot < 36; slot++) {
                int index = startIndex + slot;
                if(inv.getItem(slot) != null) {
                    BIItem biItem = BanItemAPI.getItem(inv.getItem(slot));
                    biItem.setCraftItem(index);
                } else {
                    biData.delCraftItem(index);
                }
            }
            biData.save();
            biData.removeCraftTemp();
        }
    }
}
