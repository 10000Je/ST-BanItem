package com.stuudent.banitem.commands;

import com.stuudent.banitem.BanItemAPI;
import com.stuudent.banitem.data.ItemData;
import com.stuudent.banitem.enums.BannedType;
import com.stuudent.banitem.interfaces.BanPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class UserCommands implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        if(cmd.getName().equals("사용금지템")) {
            if(!(sender instanceof Player)) return false;
            Player player = (Player) sender;
            BanPlayer banPlayer = BanItemAPI.getPlayer(player);
            banPlayer.setCurrentSeeingType(BannedType.RIGHT_CLICK);
            banPlayer.setCurrentSeeingPage(1);
            player.openInventory(ItemData.getBannedItemsGUI(banPlayer.getCurrentSeeingType(), false, banPlayer.getCurrentSeeingPage()));
            return false;
        }
        else if(cmd.getName().equals("조합금지템")) {
            if(!(sender instanceof Player)) return false;
            Player player = (Player) sender;
            BanPlayer banPlayer = BanItemAPI.getPlayer(player);
            banPlayer.setCurrentSeeingType(BannedType.CRAFT);
            banPlayer.setCurrentSeeingPage(1);
            player.openInventory(ItemData.getBannedItemsGUI(banPlayer.getCurrentSeeingType(), false, banPlayer.getCurrentSeeingPage()));
            return false;
        }
        else {
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        return null;
    }
}
