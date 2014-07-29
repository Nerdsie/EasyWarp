package com.collinsrichard.easywarp;

import com.collinsrichard.easywarp.commands.*;
import com.collinsrichard.easywarp.managers.FileManager;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class EasyWarp extends JavaPlugin {
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new EWListener(this),
				this);

		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
		}

		saveDefaultConfig();
		saveConfig();
		reloadConfig();
		Settings.load(this);

		FileManager fm = new FileManager(this);
		Helper helper = new Helper(this);

		FileManager.loadWarps();

		getCommand("delwarp").setExecutor(new DeleteWarpCommand());
		getCommand("easywarp").setExecutor(new EasyWarpCommand());
		getCommand("listwarp").setExecutor(new ListWarpsCommand());
		getCommand("setwarp").setExecutor(new SetWarpCommand());
		getCommand("warp").setExecutor(new WarpCommand());
	}

	public void onDisable() {
		FileManager.saveWarps();
	}
}
