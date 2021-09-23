package com.stuudent.BanItem;

import com.stuudent.BanItem.data.AllData;
import com.stuudent.BanItem.data.ItemData;
import com.stuudent.BanItem.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class BanItemAPI {

    /* BIPlayer 반환 */
    public static PlayerData getPlayer(Player targetPlayer) {
        return new PlayerData(targetPlayer);
    }

    public static PlayerData getPlayer(UUID targetUUID) {
        return new PlayerData(Bukkit.getPlayer(targetUUID));
    }

    public static PlayerData getPlayer(String targetName) {
        return new PlayerData(Bukkit.getPlayer(targetName));
    }

    /* BIItem 반환 */
    public static ItemData getItem(ItemStack targetItem) {
        return new ItemData(targetItem);
    }

    public static ItemData getItem(Material targetType) {
        return new ItemData(new ItemStack(targetType));
    }

    /* BIData 반환 */
    public static AllData getData() {
        return new AllData();
    }

}
