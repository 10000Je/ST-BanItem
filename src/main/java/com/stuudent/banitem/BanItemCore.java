package com.stuudent.banitem;

import com.stuudent.banitem.commands.AdminCommands;
import com.stuudent.banitem.commands.UserCommands;
import com.stuudent.banitem.data.ItemData;
import com.stuudent.banitem.listeners.AdminListeners;
import com.stuudent.banitem.listeners.UserListeners;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class BanItemCore extends JavaPlugin {

    public static BanItemCore instance;
    public static FileConfiguration cf;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        cf = getConfig();
        setEventListeners();
        setCommandExecutors();
        setCommandTabComplete();
        Bukkit.getConsoleSender().sendMessage("§6ST§f-§4BanItem §ev" + getDescription().getVersion() + " §a플러그인이 활성화 되었습니다. §f(created by STuuDENT, Discord 민제#5894)");
    }

    @Override
    public void onDisable() {
        ItemData.save();
        Bukkit.getConsoleSender().sendMessage("§6ST§f-§4BanItem §ev" + getDescription().getVersion() + " §c플러그인이 비활성화 되었습니다. §f(created by STuuDENT, Discord 민제#5894)");
    }

    public void setCommandExecutors() {
        getCommand("사용금지템").setExecutor(new UserCommands());
        getCommand("조합금지템").setExecutor(new UserCommands());
        getCommand("금지템관리").setExecutor(new AdminCommands());
    }

    public void setCommandTabComplete() {
        getCommand("사용금지템").setTabCompleter(new UserCommands());
        getCommand("조합금지템").setExecutor(new UserCommands());
        getCommand("금지템관리").setTabCompleter(new AdminCommands());
    }

    public void setEventListeners() {
        Bukkit.getPluginManager().registerEvents(new AdminListeners(), instance);
        Bukkit.getPluginManager().registerEvents(new UserListeners(), instance);
    }
}
