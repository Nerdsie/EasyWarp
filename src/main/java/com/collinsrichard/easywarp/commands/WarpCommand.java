package com.collinsrichard.easywarp.commands;

import com.collinsrichard.easywarp.Helper;
import com.collinsrichard.easywarp.managers.WarpManager;
import com.collinsrichard.easywarp.objects.Warp;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Error: /" + label + " <warp> || /" + label + " <player> <warp>");
            sender.sendMessage(ChatColor.RED + "Help: To list warps, use /warps");
            return true;
        }

        if (args.length >= 1) {
            Player target = null;
            String warpName = args[0];

            if (args.length > 1) {
                if (!sender.hasPermission("easywarp.command.warpother")) {
                    sender.sendMessage(ChatColor.RED + "Error: You don't have permission to do this. \"easywarp.command.warpother\"");
                    return true;
                }

                target = Bukkit.getPlayerExact(args[0]);
                warpName = args[1];

                if (target == null || !target.isOnline()) {
                    sender.sendMessage(ChatColor.RED + "Error: Player not found.");
                    return true;
                }
            } else {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "Error: You must be a player to do this.");
                    return true;
                }

                if (!sender.hasPermission("easywarp.command.warp")) {
                    sender.sendMessage(ChatColor.RED + "Error: You don't have permission to do this. \"easywarp.command.warp\"");
                    return true;
                }

                if (!WarpManager.getAvailable(sender).contains(warpName)) {
                    sender.sendMessage(ChatColor.RED + "Error: You don't have permission to go to this warp.");
                    sender.sendMessage(ChatColor.RED + "Help: To list available warps, use /warps");
                    return true;
                }

                target = (Player) sender;
            }

            if (!WarpManager.isWarp(warpName)) {
                sender.sendMessage(ChatColor.RED + "Error: Warp not found. /warps");
                return true;
            }

            Warp to = WarpManager.getWarp(warpName);

            if ((!(sender instanceof Player) || ((Player) sender) != target)) {
                sender.sendMessage(Helper.getPrefix() + ChatColor.RED + target.getName() + ChatColor.GREEN + " has been warped to " + ChatColor.RED + to.getName() + ".");

                Helper.warpOther(target, to);
            } else {
                Helper.warpSelf(target, to);
            }
        }

        return true;
    }
}