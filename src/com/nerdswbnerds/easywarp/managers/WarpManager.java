package com.nerdswbnerds.easywarp.managers;

import com.nerdswbnerds.easywarp.Settings;
import com.nerdswbnerds.easywarp.objects.Warp;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.logging.Level;

public class WarpManager {
	private static ArrayList<Warp> warps = new ArrayList<Warp>();

	public static void addWarp(String n, Location l){
		if(!isWarp(n)){
			warps.add(new Warp(n, l));
		}
	}

	public static void addWarp(Warp w){
		if(w != null && !isWarp(w.getName())){
			warps.add(w);
		}
	}

	public static Warp getWarp(String n){
		if(isWarp(n)){
			for(Warp w: getWarps()){
				if(w.getName().equalsIgnoreCase(n))
					return w;
			}
		}

		return null;
	}

	public static void removeWarp(Warp w){
		if(isWarp(w)){
			getWarps().remove(w);
		}
	}

	public static boolean isWarp(String n){
		for(Warp w: getWarps()){
			if(w.getName().equalsIgnoreCase(n))
				return true;
		}

		return false;
	}

	public static boolean isWarp(Warp w){
		return getWarps().contains(w);
	}

	public static ArrayList<Warp> getWarps(){
		return warps;
	}

	public static Warp parse(String s){
		String args[] = s.split(",");

		String name = args[0];
		String worldS = args[1];
		String xS = args[2];
		String yS = args[3];
		String zS = args[4];
		String pitchS = args[5];
		String yawS = args[6];

		try{
			World world = Bukkit.getWorld(worldS);
			double x = Double.parseDouble(xS);
			double y = Double.parseDouble(yS);
			double z = Double.parseDouble(zS);
			Float pitch = Float.parseFloat(pitchS);
			Float yaw = Float.parseFloat(yawS);

			Location loc = new Location(world, x, y, z, pitch, yaw);

			return new Warp(name, loc);
		}catch(Exception e){
			Bukkit.getLogger().log(Level.WARNING, "Error parsing warp '" + name + "'");
			return null;
		}
	}

	public static ArrayList<Warp> getAvailable(CommandSender s){
		if(!Settings.perWarpPerms || s.hasPermission("easywarp.warp.*")){
			return getWarps();
		}

		ArrayList<Warp> ret = new ArrayList<Warp>();

		for(Warp w: getWarps()){
			if(s.hasPermission("easywarp.warp." + w.getName().toLowerCase())){
				ret.add(w);
			}
		}

		return ret;
	}

}
