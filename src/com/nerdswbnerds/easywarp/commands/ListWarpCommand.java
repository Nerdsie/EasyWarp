package com.nerdswbnerds.easywarp.commands;

import com.nerdswbnerds.easywarp.Utils;
import com.nerdswbnerds.easywarp.managers.WarpManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ListWarpCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
		if (cmd.getName().equalsIgnoreCase("listwarps")) {
			if (!sender.hasPermission("easywarp.command.listwarps")) {
				sender.sendMessage(ChatColor.RED + "Error: You need the 'easywarp.command.listwarps' permission node to do this.");
				return true;
			}

			int page = 1;

			if (args.length > 0) {
				try {
					page = Integer.parseInt(args[0]);
				} catch (Exception e) {
					sender.sendMessage(ChatColor.RED + "Error: /" + label + " <page#>");
					return true;
				}
			}

			page = Math.min(page, (((WarpManager.getAvailable(sender).size() - 1) / 8) + 1));

			sender.sendMessage(Utils.getPrefix() + "Available warps " + ChatColor.RED + "(" + page + "/" + (((WarpManager.getAvailable(sender).size() - 1) / 8) + 1) + ")");

			for (int i = ((page - 1) * 8); i < Math.min(WarpManager.getAvailable(sender).size(), ((page - 1) * 8) + 8); i++) {
				sender.sendMessage(ChatColor.AQUA + "" + (i + 1) + ". " + ChatColor.GREEN + WarpManager.getAvailable(sender).get(i).getName());
			}

			return true;
		}

		return false;
	}
}
