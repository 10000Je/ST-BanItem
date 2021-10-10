package com.stuudent.banitem.listeners;

import com.stuudent.banitem.BanItemAPI;
import com.stuudent.banitem.BanItemCore;
import com.stuudent.banitem.data.ItemData;
import com.stuudent.banitem.enums.BannedType;
import com.stuudent.banitem.event.BlockedEvent;
import com.stuudent.banitem.interfaces.BanItem;
import com.stuudent.banitem.interfaces.BanPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class UserListeners implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        BanPlayer banPlayer = BanItemAPI.getPlayer(player);
        if(banPlayer.isSeeingBanItems()) {
            e.setCancelled(true);
            int page = banPlayer.getCurrentSeeingPage();
            if (e.getRawSlot() == 45 && page != 1) {
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                BannedType bannedType = banPlayer.getCurrentSeeingType();
                player.openInventory(ItemData.getBannedItemsGUI(bannedType, false, page - 1));
                banPlayer.setCurrentSeeingType(bannedType);
                banPlayer.setCurrentSeeingPage(page - 1);
            }
            else if (e.getRawSlot() == 53) {
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                BannedType bannedType = banPlayer.getCurrentSeeingType();
                player.openInventory(ItemData.getBannedItemsGUI(bannedType, false, page + 1));
                banPlayer.setCurrentSeeingType(bannedType);
                banPlayer.setCurrentSeeingPage(page + 1);
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        BanPlayer banPlayer = BanItemAPI.getPlayer(player);
        if(banPlayer.isSeeingBanItems()) {
            banPlayer.resetCurrentSeeingType();
            banPlayer.resetCurrentSeeingPage();
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if(!e.getAction().equals(Action.RIGHT_CLICK_AIR) && !e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if(e.getPlayer().hasPermission(BanItemCore.cf.getString("Permission"))) return;
        ItemStack heldItem = e.getPlayer().getInventory().getItemInMainHand();
        BanItem banItem = BanItemAPI.getItem(heldItem);
        if(banItem.isItemBanned(BannedType.RIGHT_CLICK)) {
            BlockedEvent event = new BlockedEvent(e.getPlayer(), heldItem, BannedType.RIGHT_CLICK);
            Bukkit.getPluginManager().callEvent(event);
            if(event.isCancelled())
                return;
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', BanItemCore.cf.getString("RightClickBannedMessage", "")));
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if(e.getWhoClicked().hasPermission(BanItemCore.cf.getString("Permission"))) return;
        ItemStack craftedItem = e.getCurrentItem();
        BanItem banItem = BanItemAPI.getItem(craftedItem);
        if(banItem.isItemBanned(BannedType.CRAFT)) {
            BlockedEvent event = new BlockedEvent((Player) e.getWhoClicked(), craftedItem, BannedType.CRAFT);
            Bukkit.getPluginManager().callEvent(event);
            if(event.isCancelled())
                return;
            e.setCancelled(true);
            e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', BanItemCore.cf.getString("CraftBannedMessage", "")));
        }
    }

}
