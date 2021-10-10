package com.stuudent.banitem.interfaces;

import com.stuudent.banitem.enums.BannedType;

public interface BanItem {

    /**
     * @param index is the index of your item will be saved.
     */
    void setItemBanned(BannedType bannedType, int index);

    /**
     * @return returns whether this item is banned or not.
     */
    boolean isItemBanned(BannedType bannedType);

}
