package com.collinsrichard.easywarp.managers;

import com.collinsrichard.easywarp.EasyWarp;
import com.collinsrichard.easywarp.objects.Warp;

import java.io.*;
import java.util.ArrayList;

public class FileManager {
	private static EasyWarp plugin;
	
	public FileManager(EasyWarp instance){
		FileManager.plugin = instance;
	}
    public static void loadWarps() {
        String fName = "Warps.data";

        File file = new File(plugin.getDataFolder().getAbsolutePath() + "\\" + fName);

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

                            ois.close();
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

    public static void saveWarps() {
        String fName = "Warps.data";

        ArrayList<String> format = new ArrayList<String>();

        for (Warp w : WarpManager.getWarpObjects()) {
            format.add(w.toString());
        }

        File file = new File(plugin.getDataFolder().getAbsolutePath() +"\\" + fName);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Easy Warp had an error saving warps.");
            }
        }

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file.getAbsolutePath()));
            oos.writeObject(format);
            oos.flush();
            oos.close();
        } catch (Exception e) {
            System.out.println("Easy Warp had an error saving warps.");
        }
    }

}
