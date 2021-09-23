package com.stuudent.BanItem.commands;

import com.stuudent.BanItem.BanItemAPI;
import com.stuudent.BanItem.data.AllData;
import com.stuudent.BanItem.data.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class UserCommands implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        if(cmd.getName().equals("사용금지템")) {
            if(!(sender instanceof Player))
                return false;
            Player player = (Player) sender;
            PlayerData playerData = BanItemAPI.getPlayer(player);
            AllData allData = BanItemAPI.getData();
            int page = 1;
            playerData.setUserRightClickState();
            playerData.setUserRightClickPage(page);
            player.openInventory(allData.getRightClickInventory(true, page));
            return false;
        }
        else if(cmd.getName().equals("조합금지템")) {
            if(!(sender instanceof Player))
                return false;
            Player player = (Player) sender;
            PlayerData playerData = BanItemAPI.getPlayer(player);
            AllData allData = BanItemAPI.getData();
            int page = 1;
            playerData.setUserCraftState();
            playerData.setUserCraftPage(page);
            player.openInventory(allData.getCraftInventory(true, page));
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
