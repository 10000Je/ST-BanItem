package com.stuudent.banitem.listeners;

import com.stuudent.banitem.BanItemAPI;
import com.stuudent.banitem.data.ItemData;
import com.stuudent.banitem.enums.BannedType;
import com.stuudent.banitem.interfaces.BanItem;
import com.stuudent.banitem.interfaces.BanPlayer;
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
        BanPlayer banPlayer = BanItemAPI.getPlayer(player);
        if(banPlayer.isSettingBanItems()) {
            if (e.getRawSlot() >= 36 && e.getRawSlot() <= 53) {
                e.setCancelled(true);
                int page = banPlayer.getCurrentSettingPage();
                if (e.getRawSlot() == 45 && page != 1) {
                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                    BannedType bannedType = banPlayer.getCurrentSettingType();
                    player.openInventory(ItemData.getBannedItemsGUI(bannedType, true, page - 1));
                    banPlayer.setCurrentSettingType(bannedType);
                    banPlayer.setCurrentSettingPage(page - 1);
                }
                else if (e.getRawSlot() == 53) {
                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                    BannedType bannedType = banPlayer.getCurrentSettingType();
                    player.openInventory(ItemData.getBannedItemsGUI(bannedType, true, page + 1));
                    banPlayer.setCurrentSettingType(bannedType);
                    banPlayer.setCurrentSettingPage(page + 1);
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        BanPlayer banPlayer = BanItemAPI.getPlayer(player);
        if(banPlayer.isSettingBanItems()) {
            int startIndex = 36 * (banPlayer.getCurrentSettingPage() - 1);
            Inventory inv = e.getInventory();
            for(int slot=0; slot < 36; slot++) {
                int index = startIndex + slot;
                if(inv.getItem(slot) != null) {
                    BanItem banItem = BanItemAPI.getItem(inv.getItem(slot));
                    banItem.setItemBanned(banPlayer.getCurrentSettingType(), index);
                } else {
                    ItemData.resetBannedItem(banPlayer.getCurrentSettingType(), index);
                }
            }
            banPlayer.resetCurrentSettingType();
            banPlayer.resetCurrentSettingPage();
        }
    }
}
