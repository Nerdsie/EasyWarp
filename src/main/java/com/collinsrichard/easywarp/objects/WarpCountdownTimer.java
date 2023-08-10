package com.collinsrichard.easywarp.objects;

import com.collinsrichard.easywarp.Helper;
import com.collinsrichard.easywarp.Settings;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class WarpCountdownTimer extends WarpTimer{
	public int secondsRemaining = 0;

	public WarpCountdownTimer(Player p, Warp w, int seconds) {
		super(p, w);
		secondsRemaining = seconds;
	}

	@Override
	public void run() {
		secondsRemaining--;

		if(secondsRemaining <= 0) {
			super.run();
			return;
		}

		HashMap<String, String> values = new HashMap<String, String>();
		values.put("time", Integer.toString(secondsRemaining));
		Helper.sendParsedMessage(player, Settings.getMessage("warp.countdown"), values);
	}
}
