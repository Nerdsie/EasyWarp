package com.nerdswbnerds.easywarp;

import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class Settings {
	public static int delay = 0;
	public static boolean perWarpPerms = false;
	public static boolean signsReqPerms = false;
	public static boolean signsPerWarpPerms = false;
	public static boolean permsBypassDelay = false;
	public static boolean opsBypassDelay = false;
	public static boolean warpOtherBypassDelay = false;
	public static boolean signsBypassDelay = false;
	public static String serverName = "EasyWarp";

	public static void load(EasyWarp plugin) {
		loadSettings(plugin);
	}

	public static void loadSettings(Plugin plugin) {
		try {
			Settings.serverName = plugin.getConfig().getString("server-name");
			Settings.delay = plugin.getConfig().getInt("warp-delay");
			Settings.perWarpPerms = plugin.getConfig().getBoolean("per-warp-permissions");
			Settings.signsReqPerms = plugin.getConfig().getBoolean("signs-require-permissions");
			Settings.signsPerWarpPerms = plugin.getConfig().getBoolean("signs-per-warp-permissions");
			Settings.permsBypassDelay = plugin.getConfig().getBoolean("permissions-bypass-delay");
			Settings.opsBypassDelay = plugin.getConfig().getBoolean("ops-bypass-delay");
			Settings.warpOtherBypassDelay = plugin.getConfig().getBoolean("warp-other-bypass-delay");
			Settings.signsBypassDelay = plugin.getConfig().getBoolean("signsr-bypass-delay");
		} catch (Exception e) {
			plugin.getLogger().log(Level.WARNING, "Error loading config: using defaults.");
		}
	}
}
