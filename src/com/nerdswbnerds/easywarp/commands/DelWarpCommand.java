package com.nerdswbnerds.easywarp.commands;

import com.nerdswbnerds.easywarp.Utils;
import com.nerdswbnerds.easywarp.managers.FileManager;
import com.nerdswbnerds.easywarp.managers.WarpManager;
import com.nerdswbnerds.easywarp.objects.Warp;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DelWarpCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
		if (cmd.getName().equalsIgnoreCase("delwarp")) {
			if (!WarpManager.isWarp(args[0])) {
				sender.sendMessage(ChatColor.RED + "Error: This warp doesn't exist.");
				return true;
			}

			if (!sender.hasPermission("easywarp.command.delwarp")) {
				sender.sendMessage(ChatColor.RED + "Error: You need the 'easywarp.command.delwarp' permission node to do this.");
				return true;
			}

			Warp remove = WarpManager.getWarp(args[0]);

			sender.sendMessage(Utils.getPrefix() + "The warp " + ChatColor.RED + remove.getName() + ChatColor.GREEN + " has been removed.");

			WarpManager.removeWarp(remove);
			FileManager.saveWarps();

			return true;
		}

		return false;
	}
}