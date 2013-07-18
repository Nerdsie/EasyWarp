package com.nerdswbnerds.easywarp;

import com.nerdswbnerds.easywarp.managers.WarpManager;
import com.nerdswbnerds.easywarp.objects.Warp;
import com.nerdswbnerds.easywarp.objects.WarpTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Utils {
	public static ArrayList<WarpTimer>	warpTimers	= new ArrayList<WarpTimer>();

	public static boolean canImport() {
		String fName = "warps.data";

		File file = new File("plugins/SimpleWarps/" + fName);

		return file.exists();
	}

	public static void importWarps() {
		String fName = "warps.data";

		File file = new File("plugins/SimpleWarps/" + fName);

		if (file.exists()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
				Object result = ois.readObject();

				ois.close();
				if (result != null) {
					@SuppressWarnings("unchecked")
					ArrayList<String> parse = (ArrayList<String>) result;

					for (String i : parse) {
						try {
							String[] args = i.split(",");

							Location warpL = new Location(
									Bukkit.getWorld(args[1]),
									Integer.parseInt(args[2]),
									Integer.parseInt(args[3]),
									Integer.parseInt(args[4]),
									Float.parseFloat(args[5]),
									Float.parseFloat(args[6]));
							Warp warp = new Warp(args[0], warpL);

							WarpManager.addWarp(warp);

							ois.close();
						} catch (Exception e) {
							System.out.println("Error importing SimpleWarps.");
						}
					}
				}
			} catch (Exception e) {
				System.out.println("Error importing SimpleWarps.");
			}
		}
	}

	public static String getPrefix() {
		return ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + Settings.serverName + ChatColor.DARK_AQUA + "] " + ChatColor.GREEN;
	}

	public static void warpOther(Player player, Warp to) {
		if (Settings.delay == 0 || (Settings.warpOtherBypassDelay) || (Settings.opsBypassDelay && player.isOp()) || (Settings.permsBypassDelay && player.hasPermission("easywarp.delay.bypass"))) {
			player.teleport(to.getLocation());
			player.sendMessage(getPrefix() + "You have been warped to " + ChatColor.RED + to.getName());
			return;
		}

		delayedTeleport(player, to);
	}

	public static void warpSelf(Player player, Warp to) {
		if (Settings.delay == 0 || (Settings.opsBypassDelay && player.isOp()) || (Settings.permsBypassDelay && player.hasPermission("easywarp.delay.bypass"))) {
			player.teleport(to.getLocation());
			player.sendMessage(getPrefix() + "You have been warped to " + ChatColor.RED + to.getName());
			return;
		}

		delayedTeleport(player, to);
	}

	public static void warpSign(Player player, Warp to) {
		if (Settings.delay == 0 || (Settings.opsBypassDelay && player.isOp()) || (Settings.permsBypassDelay && player.hasPermission("easywarp.delay.bypass")) || (Settings.signsBypassDelay)) {

			player.teleport(to.getLocation());

			player.sendMessage(getPrefix() + "You have been warped to " + ChatColor.RED + to.getName());
		}

		delayedTeleport(player, to);
	}

	public static void delayedTeleport(Player player, Warp to) {
		player.sendMessage(getPrefix() + "You will be warped in " + ChatColor.RED + Settings.delay + " seconds. " + ChatColor.GREEN + "Do not move.");

		if (isWarping(player)) {
			stopWarping(player);
		}

		WarpTimer warpTimer = new WarpTimer(player, to);
		warpTimer.id = Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Easy Warp"), warpTimer, 20L * Settings.delay);

		warpTimers.add(warpTimer);
	}

	public static boolean isWarping(Player p) {
		for (WarpTimer t : warpTimers) {
			if (t.player.getName().equalsIgnoreCase(p.getName())) {
				return true;
			}
		}

		return false;
	}

	public static void stopWarping(Player p) {
		if (isWarping(p)) {
			WarpTimer wT = null;

			for (WarpTimer t : warpTimers) {
				if (t.player.getName().equalsIgnoreCase(p.getName())) {
					wT = t;
					break;
				}
			}

			if (wT != null) {
				Bukkit.getScheduler().cancelTask(wT.id);
				warpTimers.remove(wT);
			}
		}
	}

	public static Plugin getPlugin() {
		return Bukkit.getPluginManager().getPlugin("Easy Warp");
	}
}
