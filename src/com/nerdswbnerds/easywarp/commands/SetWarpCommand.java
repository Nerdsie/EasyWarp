package com.nerdswbnerds.easywarp.commands;

import com.nerdswbnerds.easywarp.Utils;
import com.nerdswbnerds.easywarp.managers.FileManager;
import com.nerdswbnerds.easywarp.managers.WarpManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarpCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
		if (cmd.getName().equalsIgnoreCase("setwarp")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Error: You must be a player to do this.");
				return true;
			}

			if (WarpManager.isWarp(args[0])) {
				sender.sendMessage(ChatColor.RED + "Error: This warp already exists. /delwarp");
				return true;
			}

			if (!sender.hasPermission("easywarp.command.setwarp")) {
				sender.sendMessage(ChatColor.RED + "Error: You need the 'easywarp.command.setwarp' permission node to do this.");
				return true;
			}

			Player player = (Player) sender;

			WarpManager.addWarp(args[0], player.getLocation());

			player.sendMessage(Utils.getPrefix() + "The warp " + ChatColor.RED + args[0] + ChatColor.GREEN + " has been added.");
			FileManager.saveWarps();

			return true;
		}

		return false;
	}
}