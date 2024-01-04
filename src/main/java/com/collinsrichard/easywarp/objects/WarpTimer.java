package com.collinsrichard.easywarp.objects;

import com.collinsrichard.easywarp.Helper;
import com.collinsrichard.easywarp.Settings;
import com.collinsrichard.easywarp.managers.CooldownManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class WarpTimer implements Runnable {
	public Player player = null;
	public Warp warp = null;
	public boolean triggerCooldown = false;

	public int id = 0;

	public WarpTimer(Player p, Warp w, boolean triggerCooldown) {
		player = p;
		warp = w;
		this.triggerCooldown = triggerCooldown;
	}

	@Override
	public void run() {
		Helper.stopWarping(player);

		if(triggerCooldown && !Helper.playerBypassesDelays(player)) {
			CooldownManager.setCooldown(player, Settings.cooldown);
		}
        Helper.warp(player, warp);
	}

}
