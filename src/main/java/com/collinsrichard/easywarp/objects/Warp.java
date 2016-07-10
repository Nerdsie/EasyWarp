package com.collinsrichard.easywarp.objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Warp {
	private String name = "";
	private String worldName = "";
	private double x = 0, y = 0, z = 0;
	private float pitch = 0F, yaw = 0F;

	public Warp(String n, Location l) {
		setName(n);
		setLocation(l);
	}

	public Warp (String n, String world, double xx, double yy, double zz){
		setName(n);
		worldName = world;
		x = xx;
		y = yy;
		z = zz;
	}

	public Warp (String n, String world, double xx, double yy, double zz, float yyaw, float ppitch){
		setName(n);
		worldName = world;
		x = xx;
		y = yy;
		z = zz;
		yaw = yyaw;
		pitch = ppitch;
	}

	public void setName(String s) {
		name = s;
	}

	public void setLocation(Location l) {
		worldName = l.getWorld().getName();
		x = l.getX();
		y = l.getY();
		z = l.getZ();
		pitch = l.getPitch();
		yaw = l.getYaw();
	}

	public String getName() {
		return name;
	}

	public Location getLocation() {
		return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder(name + ",");

		String world = getLocation().getWorld().getName();
		String x = getLocation().getX() + "";
		String y = getLocation().getY() + "";
		String z = getLocation().getZ() + "";
		String pitch = getLocation().getPitch() + "";
		String yaw = getLocation().getYaw() + "";

		sb.append(world + ",");
		sb.append(x + ",");
		sb.append(y + ",");
		sb.append(z + ",");
		sb.append(pitch + ",");
		sb.append(yaw + ",");

		return sb.toString();
	}
}
