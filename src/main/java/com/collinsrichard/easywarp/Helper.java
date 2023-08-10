package com.collinsrichard.easywarp;

import com.collinsrichard.easywarp.objects.Warp;
import com.collinsrichard.easywarp.objects.WarpCountdownTimer;
import com.collinsrichard.easywarp.objects.WarpTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class Helper {
    public static ArrayList<WarpTimer> warpTimers = new ArrayList<WarpTimer>();

    public static ArrayList<String> getChildren(FileConfiguration config, String path) {
        ArrayList<String> toRet = new ArrayList<String>();

        try {
            for (String s : config.getConfigurationSection(path).getKeys(false)) {
                toRet.add(s);
            }
        } catch (Exception e) {
            toRet.clear();
        }

        return toRet;
    }


    public static String parse(String p) {
        String ret = ChatColor.translateAlternateColorCodes('&', p);
        return ret;
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

        try {
            if (!to.getLocation().getChunk().isLoaded()) {
                to.getLocation().getChunk().load();
            }
        }catch(Exception e){

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

        WarpTimer warpTimer;
        if(Settings.displayCountdown){
            warpTimer = new WarpCountdownTimer(player, to, Settings.delay);
            warpTimer.id = Bukkit.getScheduler().scheduleSyncRepeatingTask(getPlugin(), warpTimer, 20L, 20L);
        }else{
            warpTimer = new WarpTimer(player, to);
            warpTimer.id = Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), warpTimer, 20L * Settings.delay);
        }

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
