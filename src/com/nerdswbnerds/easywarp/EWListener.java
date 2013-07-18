package com.nerdswbnerds.easywarp;

import com.nerdswbnerds.easywarp.managers.WarpManager;
import com.nerdswbnerds.easywarp.objects.Warp;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class EWListener implements Listener {
	public EasyWarp plugin;
	
	public EWListener(EasyWarp pt) {
		plugin = pt;
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		Player player = e.getPlayer();
		
		if(e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockY() != e.getTo().getBlockY() || e.getFrom().getBlockZ() != e.getTo().getBlockZ()){
			cancelWarps(player);
		}
	}
	
	@EventHandler
	public void onHurt(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			Player player = (Player) e.getEntity();
			
			cancelWarps(player);
		}
	}
	
	public void cancelWarps(Player player){
		if(Utils.isWarping(player)){
			Utils.stopWarping(player);
			
			player.sendMessage(ChatColor.RED + "Delayed warp has been cancelled");
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(e.getClickedBlock().getType() == Material.WALL_SIGN || e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType() == Material.SIGN){
				Player player = e.getPlayer();
				
				Sign sign = (Sign) e.getClickedBlock().getState();
				
				if(match(sign.getLine(0), new String[] {"[EASY WARP]"})){
					
					String warpN = ChatColor.stripColor(sign.getLine(1));
					
					if(!WarpManager.isWarp(warpN)){
						sign.setLine(0, ChatColor.DARK_RED + "!ERROR!");
						sign.setLine(1, "Warp does");
						sign.setLine(2, "not exist.");
						sign.setLine(3, "");

						return;
					}
					
					if(Settings.signsReqPerms && !player.hasPermission("easywarp.sign.use")){
						player.sendMessage(ChatColor.RED + "Error: You need the 'easywarp.signs.use' permission node to do this.");
						return;
					}
					
					if(Settings.signsPerWarpPerms && !player.hasPermission("easywarp.warp." + warpN)){
						player.sendMessage(ChatColor.RED + "Error: You need the 'easywarp.warp." + warpN + "' permission node to do this.");
						return;
					}
					
					Warp warp = WarpManager.getWarp(warpN);

					Utils.warpSign(player, warp);
				}
			}
		}
	}
	
	@EventHandler
	public void onSignCreate(SignChangeEvent e){
		Player player = e.getPlayer();

		if(match(e.getLine(0), new String[] {"[EASYWARP]", "[EW]", "[EASY WARP]", "[EWARP]", "[E WARP]", "[E W}"})){
			if(!player.hasPermission("easywarp.sign.create")){
				e.setLine(0, ChatColor.DARK_RED + "!ERROR!");
				e.setLine(1, "You do not have");
				e.setLine(2, "permission to");
				e.setLine(3, "make warp signs");
				
				return;
			}

			String warpN = e.getLine(1);
			
			if(!WarpManager.isWarp(warpN)){
				e.setLine(0, ChatColor.DARK_RED + "!ERROR!");
				e.setLine(1, "This warp");
				e.setLine(2, "does not");
				e.setLine(3, "exist");
			}else{
				e.setLine(0, ChatColor.WHITE + "[EASY WARP]");
				e.setLine(1, ChatColor.BOLD + e.getLine(1));
				e.setLine(2, ChatColor.DARK_GRAY + e.getLine(2));
				e.setLine(3, ChatColor.DARK_GRAY + e.getLine(3));
				
				player.sendMessage(Utils.getPrefix() + "You have created a warp sign.");
			}
		}
	}
	
	public boolean match(String x, String[] split){
		String xx = ChatColor.stripColor(x);
		
		for(String y: split){
			String yy = ChatColor.stripColor(y);
			
			if(xx.equalsIgnoreCase(yy)){
				return true;
			}
		}
		
		return false;
	}
}
