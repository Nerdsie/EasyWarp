package com.collinsrichard.easywarp;

import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
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
    public static boolean warpRequiresPerms = true;
    public static boolean listWarpsRequiresPerms = true;
    public static boolean setWarpRequiresPerms = true;
    public static boolean deleteWarpRequiresPerms = true;
    public static boolean warpOtherRequiresPerms = true;
    public static boolean displayCountdown = false;

    public static String prefix = "&3[&6EasyWarp&3]";

    public static void load(EasyWarp plugin) {
        loadSettings(plugin);
    }

    public static void loadSettings(Plugin plugin) {
        try {
            Settings.prefix = plugin.getConfig().getString("server-name");
            Settings.delay = plugin.getConfig().getInt("warp-delay");
            Settings.displayCountdown = plugin.getConfig().getBoolean("display-countdown");
            Settings.warpRequiresPerms = plugin.getConfig().getBoolean("warp-requires-permissions", true);
            Settings.listWarpsRequiresPerms = plugin.getConfig().getBoolean("listwarps-requires-permissions", true);
            Settings.setWarpRequiresPerms = plugin.getConfig().getBoolean("setwarp-requires-permissions", true);
            Settings.deleteWarpRequiresPerms = plugin.getConfig().getBoolean("delwarp-requires-permissions", true);
            Settings.warpOtherRequiresPerms = plugin.getConfig().getBoolean("warp-others-requires-permissions", true);
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

        if (!toReturn.isEmpty()) return toReturn;

        String add = plugin.getConfig().getString("messages." + search);
        if (add != null) {
            toReturn.add(add);
        } else {
            toReturn.add("");
        }

        return toReturn;
    }
}
