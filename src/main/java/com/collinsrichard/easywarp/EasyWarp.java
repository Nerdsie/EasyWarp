package com.collinsrichard.easywarp;

import com.collinsrichard.easywarp.commands.*;
import com.collinsrichard.easywarp.managers.FileManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class EasyWarp extends JavaPlugin {
    public static String name = "";
    public static FileManager fileManager;

    public void onEnable() {
        name = this.getName();

        getServer().getPluginManager().registerEvents(new EWListener(this), this);

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
        }

        saveDefaultConfig();
        reloadConfig();
        Settings.load(this);
        fileManager = new FileManager();
        fileManager.loadWarps();

        getCommand("delwarp").setExecutor(new DeleteWarpCommand());
        getCommand("easywarp").setExecutor(new EasyWarpCommand());
        getCommand("listwarp").setExecutor(new ListWarpsCommand());
        getCommand("setwarp").setExecutor(new SetWarpCommand());
        getCommand("warp").setExecutor(new WarpCommand());
    }

    public void onDisable() {
        fileManager.saveWarps();
    }
}
