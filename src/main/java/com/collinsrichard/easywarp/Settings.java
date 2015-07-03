package com.collinsrichard.easywarp;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public class Settings {
    public static int delay = 0;
    public static boolean perWarpPerms = false;
    public static boolean signsReqPerms = false;
    public static boolean signsPerWarpPerms = false;
    public static boolean permsBypassDelay = false;
    public static boolean opsBypassDelay = false;
    public static boolean warpOtherBypassDelay = false;
    public static boolean signsBypassDelay = false;
    public static boolean canOverwrite = false;
    public static String prefix = "&3[&6EasyWarp&3]";

    public static void load(EasyWarp plugin) {
        loadSettings(plugin);
    }

    public static void loadSettings(Plugin plugin) {
        try {
            Settings.prefix = plugin.getConfig().getString("server-name");
            Settings.delay = plugin.getConfig().getInt("warp-delay");
            Settings.perWarpPerms = plugin.getConfig().getBoolean("per-warp-permissions");
            Settings.signsReqPerms = plugin.getConfig().getBoolean("signs-require-permissions");
            Settings.signsPerWarpPerms = plugin.getConfig().getBoolean("signs-per-warp-permissions");
            Settings.permsBypassDelay = plugin.getConfig().getBoolean("permissions-bypass-delay");
            Settings.opsBypassDelay = plugin.getConfig().getBoolean("ops-bypass-delay");
            Settings.warpOtherBypassDelay = plugin.getConfig().getBoolean("warp-other-bypass-delay");
            Settings.signsBypassDelay = plugin.getConfig().getBoolean("signs-bypass-delay");
            Settings.canOverwrite = plugin.getConfig().getBoolean("allow-warp-overwrite");

        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Error loading config: disabling.");
            plugin.getPluginLoader().disablePlugin(plugin);
        }
    }

    public static List<String> getMessage(String search) {
        Plugin plugin = Helper.getPlugin();

        List<String> toReturn = plugin.getConfig().getStringList("messages." + search);

        if (toReturn == null || toReturn.isEmpty()) {
            toReturn = new ArrayList<String>();
            String add = plugin.getConfig().getString("messages." + search);
            if (add != null) {
                toReturn.add(add);
            } else {
                toReturn.add("");
            }
        }

        return toReturn;
    }
}
