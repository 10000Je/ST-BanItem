package com.stuudent.BanItem.data;

import com.stuudent.BanItem.BanItemAPI;
import org.bukkit.entity.Player;

public class BIPlayer {

    public Player targetPlayer;
    public BIData biData;

    public BIPlayer(Player targetPlayer) {
        this.targetPlayer = targetPlayer;
        this.biData = BanItemAPI.getData();
    }

    public boolean isCanSetRightClick(){
        if(this.biData.getRightClickPlayer() == null) {
            return true;
        } else {
            return this.biData.getRightClickPlayer().equals(this.targetPlayer);
        }
    }

    public boolean isSettingRightClick() {
        if(this.biData.getRightClickPlayer() == null) {
            return false;
        } else return this.biData.getRightClickPlayer().equals(this.targetPlayer);
    }

    public boolean isCanSetCraft(){
        if(this.biData.getCraftPlayer() == null) {
            return true;
        } else {
            return this.biData.getCraftPlayer().equals(this.targetPlayer);
        }
    }

    public boolean isSettingCraft() {
        if(this.biData.getCraftPlayer() == null) {
            return false;
        } else return this.biData.getCraftPlayer().equals(this.targetPlayer);
    }

    public boolean isUserWatchingRightClick() {
        return this.biData.getUserRightClickState(this.targetPlayer);
    }

    public boolean isUserWatchingCraft() {
        return this.biData.getUserCraftState(this.targetPlayer);
    }

    public int getUserRightClickPage() {
        return this.biData.getUserRightClickPage(this.targetPlayer);
    }

    public int getUserCraftPage() {
        return this.biData.getUserCraftPage(this.targetPlayer);
    }

    public void setUserRightClickState() {
        this.biData.setUserRightClickState(this.targetPlayer);
    }

    public void setUserCraftState() {
        this.biData.setUserCraftState(this.targetPlayer);
    }

    public void setUserRightClickPage(int page) {
        this.biData.setUserRightClickPage(this.targetPlayer, page);
    }

    public void setUserCraftPage(int page) {
        this.biData.setUserCraftPage(this.targetPlayer, page);
    }

    public void removeUserRightClickTemp() {
        this.biData.removeUserRightClickTemp(this.targetPlayer);
    }

    public void removeUserCraftTemp() {
        this.biData.removeUserCraftTemp(this.targetPlayer);
    }

    // 금지템 설정창 플레이어 관리

    public void setRightClickPlayer() {
        this.biData.setRightClickPlayer(this.targetPlayer);
    }

    public void setCraftPlayer() {
        this.biData.setCraftPlayer(this.targetPlayer);
    }

}
