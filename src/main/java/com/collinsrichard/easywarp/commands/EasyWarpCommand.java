package com.collinsrichard.easywarp.commands;

import com.collinsrichard.easywarp.Helper;
import com.collinsrichard.easywarp.Settings;
import com.collinsrichard.easywarp.managers.FileManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EasyWarpCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        if (args.length == 0) {
            return false;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("easywarp.command.reload")) {
                sender.sendMessage(ChatColor.RED + "Error: You need the 'easywarp.command.reload' permission node to do this.");
                return true;
            }

            FileManager.saveWarps();

            Helper.getPlugin().reloadConfig();
            Settings.loadSettings(Helper.getPlugin());

            sender.sendMessage(Helper.getPrefix() + "Config reloaded.");
        }

        return true;
    }
}