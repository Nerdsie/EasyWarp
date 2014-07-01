package com.collinsrichard.easywarp.commands;

import com.collinsrichard.easywarp.Helper;
import com.collinsrichard.easywarp.managers.FileManager;
import com.collinsrichard.easywarp.managers.WarpManager;
import com.collinsrichard.easywarp.objects.Warp;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DeleteWarpCommand implements CommandExecutor {

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

            sender.sendMessage(Helper.getPrefix() + "The warp " + ChatColor.RED + remove.getName() + ChatColor.GREEN + " has been removed.");

            WarpManager.removeWarp(remove);
            FileManager.saveWarps();

            return true;
        }

        return false;
    }
}