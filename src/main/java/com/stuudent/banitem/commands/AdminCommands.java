package com.stuudent.banitem.commands;

import com.stuudent.banitem.BanItemAPI;
import com.stuudent.banitem.BanItemCore;
import com.stuudent.banitem.data.ItemData;
import com.stuudent.banitem.data.PlayerData;
import com.stuudent.banitem.enums.BannedType;
import com.stuudent.banitem.interfaces.BanPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminCommands implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        if(!cmd.getName().equals("금지템관리")) return false;
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        BanPlayer banPlayer = BanItemAPI.getPlayer(player);
        if(cmd.getName().equals("금지템관리")) {
            if(args.length == 0) {
                for(String text : BanItemCore.cf.getStringList("AdminHelpMessage"))
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
                return false;
            }
            if(args[0].equals("우클릭") || args[0].equals("조합")) {
                if(args[0].equals("우클릭")) {
                    if(banPlayer.isAnotherPlayerSettingBanItems(BannedType.RIGHT_CLICK)) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', BanItemCore.cf.getString("AnotherSettingRightClickBannedItems", "")
                                .replace("%player%", PlayerData.getCurrentSettingPlayer(BannedType.RIGHT_CLICK).getName())));
                        return false;
                    }
                    banPlayer.setCurrentSettingType(BannedType.RIGHT_CLICK);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', BanItemCore.cf.getString("OpenedRightClickBannedItemsGUI")));
                }
                else if(args[0].equals("조합")) {
                    if(banPlayer.isAnotherPlayerSettingBanItems(BannedType.CRAFT)) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', BanItemCore.cf.getString("AnotherSettingCraftBannedItems", "")
                                .replace("%player%", PlayerData.getCurrentSettingPlayer(BannedType.CRAFT).getName())));
                        return false;
                    }
                    banPlayer.setCurrentSettingType(BannedType.CRAFT);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', BanItemCore.cf.getString("OpenedCraftBannedItemsGUI")));
                }
                banPlayer.setCurrentSettingPage(1);
                player.openInventory(ItemData.getBannedItemsGUI(banPlayer.getCurrentSettingType(), true, banPlayer.getCurrentSettingPage()));
                return false;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        ArrayList<String> available = new ArrayList<>();
        if(!cmd.getName().equals("금지템관리"))
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
