package com.stuudent.BanItem;

import com.stuudent.BanItem.data.BIData;
import com.stuudent.BanItem.data.BIItem;
import com.stuudent.BanItem.data.BIPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class BanItemAPI {

    /* BIPlayer 반환 */
    public static BIPlayer getPlayer(Player targetPlayer) {
        return new BIPlayer(targetPlayer);
    }

    public static BIPlayer getPlayer(UUID targetUUID) {
        return new BIPlayer(Bukkit.getPlayer(targetUUID));
    }

    public static BIPlayer getPlayer(String targetName) {
        return new BIPlayer(Bukkit.getPlayer(targetName));
    }

    /* BIItem 반환 */
    public static BIItem getItem(ItemStack targetItem) {
        return new BIItem(targetItem);
    }

    public static BIItem getItem(Material targetType) {
        return new BIItem(new ItemStack(targetType));
    }

    /* BIData 반환 */
    public static BIData getData() {
        return new BIData();
    }

}
