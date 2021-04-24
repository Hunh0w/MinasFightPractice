package fr.hunh0w.practice.managers;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

import fr.hunh0w.practice.utils.LocationBuilder;

public class GlobalsManager implements Listener {
	
	public static String prefix = null;
	public static Location spawn = null;
	public static File arenasfolder = null;
	public static File presetkitsfile = null;
	public static final String linespace = "---------------------------";
	
	public static int duelstart;
	
	public static void init() {
		prefix = ChatColor.translateAlternateColorCodes('&', ConfigManager.getConfig().getString("config.prefix"))+" ";
		duelstart = ConfigManager.getConfig().getInt("config.DUEL_START");
		initLocations();
	}
	
	public static void initLocations() {
		spawn = LocationBuilder.getLocation(ConfigManager.getConfig(), "config.spawn", true);
	}
	
	@EventHandler
	public void onWorldLoad(WorldLoadEvent e) {
		if(spawn == null) initLocations();
	}

}
