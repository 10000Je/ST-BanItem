package com.stuudent.BanItem.data;

import com.stuudent.BanItem.BanItemAPI;
import org.bukkit.entity.Player;

public class PlayerData {

    public Player targetPlayer;
    public AllData allData;

    public PlayerData(Player targetPlayer) {
        this.targetPlayer = targetPlayer;
        this.allData = BanItemAPI.getData();
    }

    public boolean isCanSetRightClick(){
        if(this.allData.getRightClickPlayer() == null) {
            return true;
        } else {
            return this.allData.getRightClickPlayer().equals(this.targetPlayer);
        }
    }

    public boolean isSettingRightClick() {
        if(this.allData.getRightClickPlayer() == null) {
            return false;
        } else return this.allData.getRightClickPlayer().equals(this.targetPlayer);
    }

    public boolean isCanSetCraft(){
        if(this.allData.getCraftPlayer() == null) {
            return true;
        } else {
            return this.allData.getCraftPlayer().equals(this.targetPlayer);
        }
    }

    public boolean isSettingCraft() {
        if(this.allData.getCraftPlayer() == null) {
            return false;
        } else return this.allData.getCraftPlayer().equals(this.targetPlayer);
    }

    public boolean isUserWatchingRightClick() {
        return this.allData.getUserRightClickState(this.targetPlayer);
    }

    public boolean isUserWatchingCraft() {
        return this.allData.getUserCraftState(this.targetPlayer);
    }

    public int getUserRightClickPage() {
        return this.allData.getUserRightClickPage(this.targetPlayer);
    }

    public int getUserCraftPage() {
        return this.allData.getUserCraftPage(this.targetPlayer);
    }

    public void setUserRightClickState() {
        this.allData.setUserRightClickState(this.targetPlayer);
    }

    public void setUserCraftState() {
        this.allData.setUserCraftState(this.targetPlayer);
    }

    public void setUserRightClickPage(int page) {
        this.allData.setUserRightClickPage(this.targetPlayer, page);
    }

    public void setUserCraftPage(int page) {
        this.allData.setUserCraftPage(this.targetPlayer, page);
    }

    public void removeUserRightClickTemp() {
        this.allData.removeUserRightClickTemp(this.targetPlayer);
    }

    public void removeUserCraftTemp() {
        this.allData.removeUserCraftTemp(this.targetPlayer);
    }

    // 금지템 설정창 플레이어 관리

    public void setRightClickPlayer() {
        this.allData.setRightClickPlayer(this.targetPlayer);
    }

    public void setCraftPlayer() {
        this.allData.setCraftPlayer(this.targetPlayer);
    }

}
