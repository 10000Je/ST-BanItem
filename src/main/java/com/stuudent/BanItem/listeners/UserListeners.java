package com.stuudent.BanItem.listeners;

import com.stuudent.BanItem.BanItemAPI;
import com.stuudent.BanItem.BanItemCore;
import com.stuudent.BanItem.enums.BlockedType;
import com.stuudent.BanItem.data.BIData;
import com.stuudent.BanItem.data.BIItem;
import com.stuudent.BanItem.data.BIPlayer;
import com.stuudent.BanItem.event.BlockedEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class UserListeners implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        BIPlayer biPlayer = BanItemAPI.getPlayer(player);
        BIData biData = BanItemAPI.getData();
        if(biPlayer.isUserWatchingRightClick()) {
            e.setCancelled(true);
            int page = biPlayer.getUserRightClickPage();
            if (e.getRawSlot() == 45 && page != 1) {
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                player.openInventory(biData.getRightClickInventory(true, page - 1));
                biPlayer.setUserRightClickState();
                biPlayer.setUserRightClickPage(page - 1);
                return;
            }
            if (e.getRawSlot() == 53) {
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                player.openInventory(biData.getRightClickInventory(true, page + 1));
                biPlayer.setUserRightClickState();
                biPlayer.setUserRightClickPage(page + 1);
                return;
            }
        }
        if(biPlayer.isUserWatchingCraft()) {
            e.setCancelled(true);
            int page = biPlayer.getUserCraftPage();
            if(e.getRawSlot() == 45 && page != 1) {
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                player.openInventory(biData.getCraftInventory(true, page - 1));
                biPlayer.setUserCraftState();
                biPlayer.setUserCraftPage(page - 1);
                return;
            }
            if(e.getRawSlot() == 53) {
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                player.openInventory(biData.getCraftInventory(true, page + 1));
                biPlayer.setUserCraftState();
                biPlayer.setUserCraftPage(page + 1);
            }
        }

    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        BIPlayer biPlayer = BanItemAPI.getPlayer(player);
        if(biPlayer.isUserWatchingRightClick()) {
            biPlayer.removeUserRightClickTemp();
            return;
        }
        if(biPlayer.isUserWatchingCraft()) {
            biPlayer.removeUserCraftTemp();
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if(!e.getAction().name().contains("RIGHT_CLICK"))
            return;
        if(e.getPlayer().hasPermission(BanItemCore.cf.getString("Permission")))
            return;
        ItemStack heldItem = e.getPlayer().getInventory().getItemInMainHand();
        BIItem biItem = BanItemAPI.getItem(heldItem);
        if(biItem.isRightClickBanned()) {
            BlockedEvent event = new BlockedEvent(e.getPlayer(), heldItem, BlockedType.RIGHT_CLICK);
            Bukkit.getPluginManager().callEvent(event);
            if(event.isCancelled())
                return;
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', BanItemCore.cf.getString("UsedRightClickBanned", "")));
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if(e.getWhoClicked().hasPermission(BanItemCore.cf.getString("Permission")))
            return;
        ItemStack craftedItem = e.getCurrentItem();
        BIItem biItem = BanItemAPI.getItem(craftedItem);
        if(biItem.isCraftBanned()) {
            BlockedEvent event = new BlockedEvent((Player) e.getWhoClicked(), craftedItem, BlockedType.CRAFT);
            Bukkit.getPluginManager().callEvent(event);
            if(event.isCancelled())
                return;
            e.setCancelled(true);
            e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', BanItemCore.cf.getString("UsedCraftBanned", "")));
        }
    }

}
