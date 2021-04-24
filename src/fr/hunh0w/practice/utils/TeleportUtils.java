package fr.hunh0w.practice.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.hunh0w.practice.Practice;

public class TeleportUtils {
	
	public static void TeleportDelay(Player player, Location loc, int ticks) {
		if(loc == null || player == null || !player.isOnline() || ticks < 0) return;
		Bukkit.getScheduler().scheduleSyncDelayedTask(Practice.get(), new Runnable() {
			@Override
			public void run() {
				player.teleport(loc);
			}
		}, ticks);
	}

}
