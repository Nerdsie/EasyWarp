package com.collinsrichard.easywarp.commands;

import com.collinsrichard.easywarp.Helper;
import com.collinsrichard.easywarp.Settings;
import com.collinsrichard.easywarp.managers.CooldownManager;
import com.collinsrichard.easywarp.managers.WarpManager;
import com.collinsrichard.easywarp.objects.Warp;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class WarpCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        if (args.length == 0) {
            HashMap<String, String> values = new HashMap<String, String>();

            Helper.sendParsedMessage(sender, Settings.getMessage("error.no-warp-given"), values);
            return true;
        }

        Player target = null;
        String warpName = args[0];

        if (args.length > 1) {
            String perms = "easywarp.command.warpother";
            if (Settings.warpOtherRequiresPerms && !sender.hasPermission(perms)) {
                HashMap<String, String> values = new HashMap<String, String>();
                values.put("node", perms);

                Helper.sendParsedMessage(sender, Settings.getMessage("error.no-permission"), values);
                return true;
            }

            target = Bukkit.getPlayerExact(args[0]);
            warpName = args[1];

            if (!WarpManager.isWarp(warpName)) {
                HashMap<String, String> values = new HashMap<String, String>();
                values.put("warp", warpName);

                Helper.sendParsedMessage(sender, Settings.getMessage("error.no-warp"), values);
                return true;
            }

            if (target == null || !target.isOnline()) {
                HashMap<String, String> values = new HashMap<String, String>();
                values.put("target", args[0]);

                Helper.sendParsedMessage(sender, Settings.getMessage("error.no-player"), values);
                return true;
            }
        } else {
            if (!WarpManager.isWarp(warpName)) {
                HashMap<String, String> values = new HashMap<String, String>();
                values.put("warp", warpName);

                Helper.sendParsedMessage(sender, Settings.getMessage("error.no-warp"), values);
                return true;
            }

            if (!(sender instanceof Player)) {
                HashMap<String, String> values = new HashMap<String, String>();

                Helper.sendParsedMessage(sender, Settings.getMessage("error.not-player"), values);
                return true;
            }

            String perms = "easywarp.command.warp";
            if (Settings.warpRequiresPerms && !sender.hasPermission(perms)) {
                HashMap<String, String> values = new HashMap<String, String>();
                values.put("node", perms);

                Helper.sendParsedMessage(sender, Settings.getMessage("error.no-permission"), values);
                return true;
            }

            if (!WarpManager.getAvailable(sender).contains(warpName.toLowerCase())) {
                HashMap<String, String> values = new HashMap<String, String>();
                values.put("node", perms);
                values.put("warp", warpName);

                Helper.sendParsedMessage(sender, Settings.getMessage("warp.no-permission"), values);
                return true;
            }

            String cooldownString = CooldownManager.getCooldownString((Player) sender);
            if(cooldownString != null && !cooldownString.isEmpty()){
                HashMap<String, String> values = new HashMap<String, String>();
                values.put("time", CooldownManager.getCooldownString((Player) sender));

                Helper.sendParsedMessage(sender, Settings.getMessage("warp.cooldown"), values);
                return true;
            }

            target = (Player) sender;
        }

        Warp to = WarpManager.getWarp(warpName);

        if ((!(sender instanceof Player) || (sender) != target)) {
            HashMap<String, String> values = new HashMap<String, String>();
            values.put("target", target.getName());
            values.put("warp", to.getName());

            Helper.sendParsedMessage(sender, Settings.getMessage("warp.other"), values);

            Helper.warpOther(target, to);
        } else {
            CooldownManager.setCooldown(target, Settings.cooldown);
            Helper.warpSelf(target, to);
        }

        return true;
    }
}