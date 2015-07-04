package com.collinsrichard.easywarp.commands;

import com.collinsrichard.easywarp.EasyWarp;
import com.collinsrichard.easywarp.Helper;
import com.collinsrichard.easywarp.Settings;
import com.collinsrichard.easywarp.managers.FileManager;
import com.collinsrichard.easywarp.managers.WarpManager;
import com.collinsrichard.easywarp.objects.Warp;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class DeleteWarpCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        String perms = "easywarp.command.delwarp";

        if (cmd.getName().equalsIgnoreCase("delwarp")) {
            if (!WarpManager.isWarp(args[0])) {
                HashMap<String, String> values = new HashMap<String, String>();
                values.put("info", "");

                Helper.sendParsedMessage(sender, Settings.getMessage("error.no-warp"), values);
                return true;
            }

            if (!sender.hasPermission(perms)) {
                HashMap<String, String> values = new HashMap<String, String>();
                values.put("node", "perms");

                Helper.sendParsedMessage(sender, Settings.getMessage("error.no-permission"), values);
                return true;
            }

            Warp remove = WarpManager.getWarp(args[0]);

            HashMap<String, String> values = new HashMap<String, String>();
            values.put("warp", remove.getName());
            Helper.sendParsedMessage(sender, Settings.getMessage("warp.removed"), values);

            WarpManager.removeWarp(remove);
            EasyWarp.fileManager.saveWarps();

            return true;
        }

        return false;
    }
}