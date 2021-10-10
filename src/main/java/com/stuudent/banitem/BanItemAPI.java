package com.stuudent.banitem;

import com.stuudent.banitem.data.ItemData;
import com.stuudent.banitem.data.PlayerData;
import com.stuudent.banitem.interfaces.BanItem;
import com.stuudent.banitem.interfaces.BanPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class BanItemAPI {

    public static BanPlayer getPlayer(Player banPlayer) {
        return new PlayerData(banPlayer);
    }

    public static BanPlayer getPlayer(UUID banUUID) {
        return new PlayerData(Bukkit.getPlayer(banUUID));
    }

    public static BanPlayer getPlayer(String targetName) {
        return new PlayerData(Bukkit.getPlayer(targetName));
    }

    public static BanItem getItem(ItemStack targetItem) {
        return new ItemData(targetItem);
    }

    public static BanItem getItem(Material targetType) {
        return new ItemData(new ItemStack(targetType));
    }

}
