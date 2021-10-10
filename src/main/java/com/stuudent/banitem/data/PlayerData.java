package com.stuudent.banitem.data;

import com.stuudent.banitem.BanItemAPI;
import com.stuudent.banitem.enums.BannedType;
import com.stuudent.banitem.interfaces.BanPlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;


public class PlayerData implements BanPlayer {

    public static YamlConfiguration playerData;
    public Player banPlayer;

    static {
        playerData = new YamlConfiguration();
    }

    public PlayerData(Player banPlayer) {
        this.banPlayer = banPlayer;
    }

    @Override
    public Player getBukkitPlayer() {
        return this.banPlayer;
    }

    @Override
    public boolean isSettingBanItems() {
        return getCurrentSettingType() != null;
    }

    @Override
    public int getCurrentSettingPage() {
        return playerData.getInt(this.banPlayer.getUniqueId() + ".SETTING.PAGE", 1);
    }

    @Override
    public BannedType getCurrentSettingType() {
        String banned = playerData.getString(this.banPlayer.getUniqueId() + ".SETTING.TYPE", null);
        if(banned == null) return null;
        return BannedType.valueOf(banned);
    }

    @Override
    public void setCurrentSettingType(BannedType bannedType) {
        playerData.set(this.banPlayer.getUniqueId() + ".SETTING.TYPE", bannedType);
    }

    @Override
    public void setCurrentSettingPage(int page) {
        playerData.set(this.banPlayer.getUniqueId() + ".SETTING.PAGE", page);
    }

    @Override
    public void resetCurrentSettingType() {
        playerData.set(this.banPlayer.getUniqueId() + ".SETTING.TYPE", null);
    }

    @Override
    public void resetCurrentSettingPage() {
        playerData.set(this.banPlayer.getUniqueId() + ".SETTING.PAGE", null);
    }

    @Override
    public boolean isSeeingBanItems() {
        return getCurrentSeeingType() != null;
    }

    @Override
    public int getCurrentSeeingPage() {
        return playerData.getInt(this.banPlayer.getUniqueId() + ".SEEING.PAGE", 1);
    }

    @Override
    public BannedType getCurrentSeeingType() {
        String banned = playerData.getString(this.banPlayer.getUniqueId() + ".SEEING.TYPE", null);
        if(banned == null) return null;
        return BannedType.valueOf(banned);
    }

    @Override
    public void setCurrentSeeingType(BannedType bannedType) {
        playerData.set(this.banPlayer.getUniqueId() + ".SEEING.TYPE", bannedType.name());
    }

    @Override
    public void setCurrentSeeingPage(int page) {
        playerData.set(this.banPlayer.getUniqueId() + ".SEEING.PAGE", page);
    }

    @Override
    public void resetCurrentSeeingType() {
        playerData.set(this.banPlayer.getUniqueId() + ".SEEING.TYPE", null);
    }

    @Override
    public void resetCurrentSeeingPage() {
        playerData.set(this.banPlayer.getUniqueId() + ".SEEING.PAGE", null);
    }

    @Override
    public boolean isAnotherPlayerSettingBanItems(BannedType bannedType) {
        for(String uuidString : playerData.getKeys(false)) {
            try {
                BanPlayer banPlayer = BanItemAPI.getPlayer(UUID.fromString(uuidString));
                if(!banPlayer.getBukkitPlayer().equals(this.banPlayer) && banPlayer.isSettingBanItems() && banPlayer.getCurrentSettingType().equals(bannedType)) {
                    return true;
                }
            } catch (IllegalArgumentException ignored) {}
        }
        return false;
    }

    public static Player getCurrentSettingPlayer(BannedType bannedType) {
        for(String uuidString : playerData.getKeys(false)) {
            try {
                BanPlayer banPlayer = BanItemAPI.getPlayer(UUID.fromString(uuidString));
                if(banPlayer.isSettingBanItems() && banPlayer.getCurrentSettingType().equals(bannedType))
                    return banPlayer.getBukkitPlayer();
            } catch (IllegalArgumentException ignored) {}
        }
        return null;
    }


}
