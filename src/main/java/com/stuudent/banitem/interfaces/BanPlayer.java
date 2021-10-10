package com.stuudent.banitem.interfaces;

import com.stuudent.banitem.enums.BannedType;
import org.bukkit.entity.Player;

public interface BanPlayer {

    Player getBukkitPlayer();

    boolean isSettingBanItems();
    int getCurrentSettingPage();
    BannedType getCurrentSettingType();
    void setCurrentSettingType(BannedType bannedType);
    void setCurrentSettingPage(int page);
    void resetCurrentSettingType();
    void resetCurrentSettingPage();

    boolean isSeeingBanItems();
    int getCurrentSeeingPage();
    BannedType getCurrentSeeingType();
    void setCurrentSeeingType(BannedType bannedType);
    void setCurrentSeeingPage(int page);
    void resetCurrentSeeingType();
    void resetCurrentSeeingPage();

    boolean isAnotherPlayerSettingBanItems(BannedType bannedType);

}
