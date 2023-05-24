package com.collinsrichard.easywarp.commands;

import com.collinsrichard.easywarp.Helper;
import com.collinsrichard.easywarp.Settings;
import com.collinsrichard.easywarp.managers.WarpManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class ListWarpsCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        String perms = "easywarp.command.listwarps";

        if (Settings.listWarpsRequiresPerms && !sender.hasPermission(perms)) {
            HashMap<String, String> values = new HashMap<String, String>();
            values.put("node", perms);

            Helper.sendParsedMessage(sender, Settings.getMessage("error.no-permission"), values);
            return true;
        }

        int page = 1;

        if (args.length > 0) {
            try {
                page = Integer.parseInt(args[0]);
            } catch (Exception e) {
                HashMap<String, String> values = new HashMap<String, String>();
                values.put("command", label);

                Helper.sendParsedMessage(sender, Settings.getMessage("error.no-page"), values);
                return true;
            }
        }

        page = Math.min(page, (((WarpManager.getAvailable(sender).size() - 1) / 8) + 1));
        int pages = ((WarpManager.getAvailable(sender).size() - 1) / 8) + 1;

        HashMap<String, String> values = new HashMap<String, String>();
        values.put("page", page + "");
        values.put("pages", pages + "");

        Helper.sendParsedMessage(sender, Settings.getMessage("warp.list"), values);

        for (int i = ((page - 1) * 8); i < Math.min(WarpManager.getAvailable(sender).size(), ((page - 1) * 8) + 8); i++) {
            sender.sendMessage(ChatColor.AQUA + "" + (i + 1) + ". " + ChatColor.GREEN + WarpManager.getAvailable(sender).get(i));
        }

        return true;
    }
}
