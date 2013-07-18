package com.nerdswbnerds.easywarp.commands;

import com.nerdswbnerds.easywarp.Settings;
import com.nerdswbnerds.easywarp.Utils;
import com.nerdswbnerds.easywarp.managers.FileManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EasyWarpCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
		if (cmd.getName().equalsIgnoreCase("easywarp")) {
			if (args.length == 0) {
				return false;
			}

			if (args[0].equalsIgnoreCase("reload")) {
				if (!sender.hasPermission("easywarp.command.reload")) {
					sender.sendMessage(ChatColor.RED + "Error: You need the 'easywarp.command.reload' permission node to do this.");
					return true;
				}

				FileManager.saveWarps();

				Utils.getPlugin().reloadConfig();
				Settings.loadSettings(Utils.getPlugin());

				sender.sendMessage(Utils.getPrefix() + "Config reloaded.");
			}

			if (args[0].equalsIgnoreCase("import")) {
				if (!sender.hasPermission("easywarp.command.import")) {
					sender.sendMessage(ChatColor.RED + "Error: You need the 'easywarp.command.import' permission node to do this.");
					return true;
				}

				if (Utils.canImport()) {
					Utils.importWarps();

					FileManager.saveWarps();

					sender.sendMessage(Utils.getPrefix() + "SimpleWarp warps imported.");
				} else {
					sender.sendMessage(ChatColor.RED + "Error: SimpleWarps warps not found.");
				}
			}

			return true;
		}

		return false;
	}
}