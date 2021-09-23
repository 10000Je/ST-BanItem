package com.stuudent.BanItem.commands;

import com.stuudent.BanItem.BanItemAPI;
import com.stuudent.BanItem.BanItemCore;
import com.stuudent.BanItem.data.AllData;
import com.stuudent.BanItem.data.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserCommands implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        if(!cmd.getName().equals("금지템"))
            return false;
        if(!(sender instanceof Player))
            return false;
        Player player = (Player) sender;
        PlayerData playerData = BanItemAPI.getPlayer(player);
        AllData allData = BanItemAPI.getData();
        if(cmd.getName().equals("금지템")) {
            if(args.length == 0) {
                for(String text : BanItemCore.cf.getStringList("UserHelpMessage"))
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
                return false;
            }
            if(args[0].equals("우클릭")) {
                int page = 1;
                playerData.setUserRightClickState();
                playerData.setUserRightClickPage(page);
                player.openInventory(allData.getRightClickInventory(true, page));
                return false;
            }
            if(args[0].equals("조합")) {
                int page = 1;
                playerData.setUserCraftState();
                playerData.setUserCraftPage(page);
                player.openInventory(allData.getCraftInventory(true, page));
                return false;
            }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', BanItemCore.cf.getString("WrongUserCommand")));
            return false;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        ArrayList<String> available = new ArrayList<>();
        if(!cmd.getName().equals("금지템"))
            return available;
        if(!(sender instanceof Player))
            return available;
        Player player = (Player) sender;
        String perm = cmd.getPermission() == null ? "" : cmd.getPermission();
        if(!player.hasPermission(perm)) {
            return available;
        }
        List<String> FirstArgs = Arrays.asList("우클릭", "조합");
        if(args.length == 1) {
            for(String FirstArg : FirstArgs) {
                if(FirstArg.toLowerCase().startsWith(args[0]))
                    available.add(FirstArg);
            }
            return available;
        }
        return available;
    }
}
