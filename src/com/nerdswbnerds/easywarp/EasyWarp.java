package com.nerdswbnerds.easywarp;

import com.nerdswbnerds.easywarp.commands.*;
import com.nerdswbnerds.easywarp.managers.FileManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class EasyWarp extends JavaPlugin {
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new EWListener(this), this);

		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {}

		saveDefaultConfig();
		Settings.load(this);

		FileManager.loadWarps();

		getCommand("delwarp").setExecutor(new DelWarpCommand());
		getCommand("easywarp").setExecutor(new EasyWarpCommand());
		getCommand("listwarp").setExecutor(new ListWarpCommand());
		getCommand("setwarp").setExecutor(new SetWarpCommand());
		getCommand("warp").setExecutor(new WarpCommand());
	}

	public void onDisable() {
		FileManager.saveWarps();
	}
}
