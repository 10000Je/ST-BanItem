package com.stuudent.BanItem.data;

import com.stuudent.BanItem.BanItemAPI;
import org.bukkit.inventory.ItemStack;

public class ItemData {

    public ItemStack targetItem;
    public AllData allData;

    public ItemData(ItemStack targetItem) {
        this.targetItem = targetItem;
        this.allData = BanItemAPI.getData();
    }

    public void setRightClickItem(int index) {
        allData.setRightClickItem(this.targetItem, index);
    }

    public void setCraftItem(int index) {
        allData.setCraftItem(this.targetItem, index);
    }

    public boolean isRightClickBanned() {
        for(ItemStack compareItem : allData.getRightClickItems()) {
            if(this.targetItem.isSimilar(compareItem))
                return true;
        }
        return false;
    }

    public boolean isCraftBanned() {
        for(ItemStack compareItem : allData.getCraftItems()) {
            if(this.targetItem.isSimilar(compareItem))
                return true;
        }
        return false;
    }



}
