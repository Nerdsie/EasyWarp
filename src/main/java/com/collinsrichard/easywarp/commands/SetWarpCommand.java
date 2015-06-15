package com.collinsrichard.easywarp.commands;

import com.collinsrichard.easywarp.Helper;
import com.collinsrichard.easywarp.Settings;
import com.collinsrichard.easywarp.managers.FileManager;
import com.collinsrichard.easywarp.managers.WarpManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SetWarpCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        if (args.length == 0) {
            return false;
        }

        if (!(sender instanceof Player)) {
            HashMap<String, String> values = new HashMap<String, String>();

            Helper.sendParsedMessage(sender, Settings.getMessage("error.not-player"), values);
            return true;
        }

        if (WarpManager.isWarp(args[0]) && !Settings.canOverwrite) {
            HashMap<String, String> values = new HashMap<String, String>();

            Helper.sendParsedMessage(sender, Settings.getMessage("error.cannot-overwrite"), values);
            return true;
        }

        String perms = "easywarp.command.setwarp";
        if (!sender.hasPermission(perms)) {
            HashMap<String, String> values = new HashMap<String, String>();
            values.put("node", perms);

            Helper.sendParsedMessage(sender, Settings.getMessage("error.no-permission"), values);
            return true;
        }

        Player player = (Player) sender;

        WarpManager.addWarp(args[0], player.getLocation());
        FileManager.saveWarps();

        HashMap<String, String> values = new HashMap<String, String>();
        values.put("warp", args[0]);

        Helper.sendParsedMessage(player, Settings.getMessage("warp.set"), values);

        return true;
    }
}