package com.collinsrichard.easywarp.objects;

import com.collinsrichard.easywarp.Helper;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class WarpTimer implements Runnable {
	public Player player = null;
	public Warp warp = null;

	public int id = 0;

	public WarpTimer(Player p, Warp w) {
		player = p;
		warp = w;
	}

	@Override
	public void run() {
		Helper.stopWarping(player);

        Helper.warp(player, warp);
	}

}
