package com.stuudent.BanItem.event;

import com.stuudent.BanItem.enums.BlockedType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class BlockedEvent extends Event implements Cancellable {

    public boolean cancelled;
    public BlockedType blockedType;
    public Player usedPlayer;
    public ItemStack usedItem;
    public static HandlerList handlers;

    static {
        handlers = new HandlerList();
    }

    public BlockedEvent(Player usedPlayer, ItemStack usedItem, BlockedType blockedType) {
        this.usedPlayer = usedPlayer;
        this.usedItem = usedItem;
        this.blockedType = blockedType;
    }

    public BlockedType getType() {
        return this.blockedType;
    }

    public Player getPlayer() {
        return this.usedPlayer;
    }

    public ItemStack getItem() {
        return this.usedItem;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
