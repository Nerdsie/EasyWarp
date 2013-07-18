package com.nerdswbnerds.easywarp.objects;

import com.nerdswbnerds.easywarp.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class WarpTimer implements Runnable{
	public Player player = null;
	public Warp warp = null;
	
	public int id = 0;
	
	public WarpTimer(Player p, Warp w){
		player = p;
		warp = w;
	}
	
	@Override
	public void run() {
		player.teleport(warp.getLocation());
		player.sendMessage(Utils.getPrefix() + "You have been warped to " + ChatColor.RED + warp.getName());
	}

}
