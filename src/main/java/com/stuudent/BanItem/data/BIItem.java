package com.stuudent.BanItem.data;

import com.stuudent.BanItem.BanItemAPI;
import org.bukkit.inventory.ItemStack;

public class BIItem {

    public ItemStack targetItem;
    public BIData biData;

    public BIItem(ItemStack targetItem) {
        this.targetItem = targetItem;
        this.biData = BanItemAPI.getData();
    }

    public void setRightClickItem(int index) {
        biData.setRightClickItem(this.targetItem, index);
    }

    public void setCraftItem(int index) {
        biData.setCraftItem(this.targetItem, index);
    }

    public boolean isRightClickBanned() {
        for(ItemStack compareItem : biData.getRightClickItems()) {
            if(this.targetItem.isSimilar(compareItem))
                return true;
        }
        return false;
    }

    public boolean isCraftBanned() {
        for(ItemStack compareItem : biData.getCraftItems()) {
            if(this.targetItem.isSimilar(compareItem))
                return true;
        }
        return false;
    }



}
