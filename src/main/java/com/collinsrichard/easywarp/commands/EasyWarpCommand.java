package com.collinsrichard.easywarp.commands;

import com.collinsrichard.easywarp.EasyWarp;
import com.collinsrichard.easywarp.Helper;
import com.collinsrichard.easywarp.Settings;
import com.collinsrichard.easywarp.managers.FileManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class EasyWarpCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        if (args.length == 0) {
            return false;
        }

        String perms = "easywarp.command.reload";

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission(perms)) {
                HashMap<String, String> values = new HashMap<String, String>();
                values.put("node", perms);

                Helper.sendParsedMessage(sender, Settings.getMessage("error.no-permission"), values);
                return true;
            }

            EasyWarp.fileManager.saveWarps();

            Helper.getPlugin().reloadConfig();
            Settings.loadSettings(Helper.getPlugin());

            HashMap<String, String> values = new HashMap<String, String>();
            values.put("info", "");

            Helper.sendParsedMessage(sender, Settings.getMessage("config.reloaded"), values);
        }

        return true;
    }
}