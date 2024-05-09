package com.collinsrichard.easywarp.managers;

import com.collinsrichard.easywarp.EasyWarp;
import com.collinsrichard.easywarp.Helper;
import com.collinsrichard.easywarp.objects.Warp;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.UUID;

public class FileManager {
    private FileConfiguration warpConfig = null;
    private File warpConfigFile = null;

    public File getOldFile() {
        String fName = "warps.data";
        return new File("plugins/" + EasyWarp.name + "/" + fName);
    }

    public FileManager() {
        warpConfigFile = new File(Helper.getPlugin().getDataFolder(), "warps.yml");
        warpConfig = YamlConfiguration.loadConfiguration(warpConfigFile);
    }

    public void loadWarpsOld() {
        File file = getOldFile();

        if (file.exists()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
                Object result = ois.readObject();

                ois.close();
                if (result != null) {
                    @SuppressWarnings("unchecked")
                    ArrayList<String> parse = (ArrayList<String>) result;

                    for (String i : parse) {
                        try {
                            WarpManager.addWarp(WarpManager.deserialize(i));
                        } catch (Exception e) {
                            System.out.println("Easy Warp had an error loading warps.");
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Easy Warp had an error loading warps.");
            }
        }
    }

    public void saveWarps() {
        for(String key : warpConfig.getKeys(true)){
            warpConfig.set(key, null);
        }

        for (Warp warp : WarpManager.getWarps()) {
            warpConfig.set("warps." + warp.getName() + ".world", warp.getLocation().getWorld().getName());
            warpConfig.set("warps." + warp.getName() + ".x", warp.getLocation().getX());
            warpConfig.set("warps." + warp.getName() + ".y", warp.getLocation().getY());
            warpConfig.set("warps." + warp.getName() + ".z", warp.getLocation().getZ());

            warpConfig.set("warps." + warp.getName() + ".yaw", warp.getLocation().getYaw());
            warpConfig.set("warps." + warp.getName() + ".pitch", warp.getLocation().getPitch());
            if(warp.getOwner() != null) {
                warpConfig.set("warps." + warp.getName() + ".owner", warp.getOwner().toString());
            }
        }

        try {
            warpConfig.save(warpConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadWarps() {
        if (getOldFile().exists() && !warpConfigFile.exists()) {
            loadWarpsOld();
            getOldFile().delete();
            saveWarps();
            return;
        }

        ArrayList<String> list = Helper.getChildren(warpConfig, "warps");

        for (String name : list) {
            String worldName = warpConfig.getString("warps." + name + ".world");
            double x = warpConfig.getDouble("warps." + name + ".x");
            double y = warpConfig.getDouble("warps." + name + ".y");
            double z = warpConfig.getDouble("warps." + name + ".z");
            Float yaw = 0F, pitch = 0F;
            UUID owner = null;

            if (warpConfig.contains("warps." + name + ".yaw")) {
                yaw = Float.parseFloat(warpConfig.getString("warps." + name + ".yaw"));
            }

            if (warpConfig.contains("warps." + name + ".pitch")) {
                pitch = Float.parseFloat(warpConfig.getString("warps." + name + ".pitch"));
            }

            if (warpConfig.contains("warps." + name + ".owner")) {
                owner = UUID.fromString(warpConfig.getString("warps." + name + ".owner"));
            }

            Warp warp = new Warp(name, worldName, x, y, z, yaw, pitch, owner);
            WarpManager.addWarp(warp);
        }
    }
}
