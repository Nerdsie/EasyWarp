package com.collinsrichard.easywarp;

import com.collinsrichard.easywarp.objects.Warp;
import com.collinsrichard.easywarp.objects.WarpTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class Helper {
    public static ArrayList<WarpTimer> warpTimers = new ArrayList<WarpTimer>();

    public static String parse(String s) {
        String message = s;

        message = message.replaceAll("&0", ChatColor.BLACK + "");
        message = message.replaceAll("&1", ChatColor.DARK_BLUE + "");
        message = message.replaceAll("&2", ChatColor.DARK_GREEN + "");
        message = message.replaceAll("&3", ChatColor.DARK_AQUA + "");
        message = message.replaceAll("&4", ChatColor.DARK_RED + "");
        message = message.replaceAll("&5", ChatColor.DARK_PURPLE + "");
        message = message.replaceAll("&6", ChatColor.GOLD + "");
        message = message.replaceAll("&7", ChatColor.GRAY + "");
        message = message.replaceAll("&8", ChatColor.DARK_GRAY + "");
        message = message.replaceAll("&9", ChatColor.BLUE + "");
        message = message.replaceAll("&a", ChatColor.GREEN + "");
        message = message.replaceAll("&b", ChatColor.AQUA + "");
        message = message.replaceAll("&c", ChatColor.RED + "");
        message = message.replaceAll("&d", ChatColor.LIGHT_PURPLE + "");
        message = message.replaceAll("&e", ChatColor.YELLOW + "");
        message = message.replaceAll("&f", ChatColor.WHITE + "");
        message = message.replaceAll("&o", ChatColor.ITALIC + "");
        message = message.replaceAll("&n", ChatColor.UNDERLINE + "");
        message = message.replaceAll("&m", ChatColor.STRIKETHROUGH + "");
        message = message.replaceAll("&l", ChatColor.BOLD + "");
        message = message.replaceAll("&r", ChatColor.RESET + "");
        message = message.replaceAll("&k", ChatColor.MAGIC + "");

        return message;
    }

    public static String getPrefix() {
        return parse(Settings.prefix);
    }

    public static void warpOther(Player player, Warp to) {
        warp(player, to);
    }

    public static void warpSelf(Player player, Warp to) {
        if (Settings.delay == 0 || (Settings.opsBypassDelay && player.isOp()) || (Settings.permsBypassDelay && player.hasPermission("easywarp.delay.bypass"))) {
            warp(player, to);
            return;
        }

        delayedTeleport(player, to);
    }

    public static void warpSign(Player player, Warp to) {
        if (Settings.delay == 0 || (Settings.opsBypassDelay && player.isOp()) || (Settings.permsBypassDelay && player.hasPermission("easywarp.delay.bypass")) || (Settings.signsBypassDelay)) {

            warp(player, to);
            return;
        }

        delayedTeleport(player, to);
    }

    public static void warp(Player player, Warp to) {
        HashMap<String, String> values = new HashMap<String, String>();
        values.put("warp", to.getName());

        sendParsedMessage(player, Settings.getMessage("warp.completed"), values);

        if (!to.getLocation().getChunk().isLoaded()) {
            to.getLocation().getChunk().load();
        }

        player.teleport(to.getLocation());
    }

    public static void delayedTeleport(Player player, Warp to) {
        HashMap<String, String> values = new HashMap<String, String>();
        values.put("warp", to.getName());

        sendParsedMessage(player, Settings.getMessage("warp.delayed"), values);

        if (isWarping(player)) {
            stopWarping(player);
        }

        WarpTimer warpTimer = new WarpTimer(player, to);
        warpTimer.id = Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), warpTimer, 20L * Settings.delay);

        if (!to.getLocation().getChunk().isLoaded()) {
            to.getLocation().getChunk().load();
        }

        warpTimers.add(warpTimer);
    }

    public static boolean isWarping(Player p) {
        for (WarpTimer t : warpTimers) {
            if (t.player.getName().equalsIgnoreCase(p.getName())) {
                return true;
            }
        }

        return false;
    }

    public static void stopWarping(Player p) {
        if (isWarping(p)) {
            WarpTimer wT = null;

            for (WarpTimer t : warpTimers) {
                if (t.player.getName().equalsIgnoreCase(p.getName())) {
                    wT = t;
                    break;
                }
            }

            if (wT != null) {
                Bukkit.getScheduler().cancelTask(wT.id);
                warpTimers.remove(wT);
            }
        }
    }

    public static Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugin(EasyWarp.name);
    }

    public static void sendParsedMessage(CommandSender sender, List<String> messages, HashMap<String, String> values) {
        for (String msg : messages) {
            String message = msg;

            if (!message.equalsIgnoreCase("")) {
                for (String key : values.keySet()) {
                    message = message.replaceAll(Pattern.quote("{" + key + "}"), values.get(key));
                }

                String playerDisplay = "CONSOLE";
                String player = "CONSOLE";

                if (sender instanceof Player) {
                    playerDisplay = ((Player) sender).getDisplayName();
                    player = sender.getName();
                }

                message = message.replaceAll(Pattern.quote("{server}"), getPrefix());
                message = message.replaceAll(Pattern.quote("{name}"), player);
                message = message.replaceAll(Pattern.quote("{display}"), playerDisplay);
                message = message.replaceAll(Pattern.quote("{delay}"), Settings.delay + "");

                sender.sendMessage(Helper.parse(message));
            }
        }
    }
}
