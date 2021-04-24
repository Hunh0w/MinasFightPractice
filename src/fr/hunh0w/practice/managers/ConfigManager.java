package fr.hunh0w.practice.managers;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.hunh0w.practice.Practice;

public class ConfigManager {
	
	private static File configfile = null;
	private static FileConfiguration config = null;
	
	public static File getConfigFile() {return configfile;}
	public static FileConfiguration getConfig() {return config;}
	public static boolean saveConfig() {
		if(config != null && configfile != null) {
			try {
				config.save(configfile);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	public static void load() {
		if(!loadFiles()) return;
		GlobalsManager.init();
	}
	
	public static boolean loadFiles() {
		configfile = new File(Practice.get().getDataFolder(), "config.yml");

	  	if (!configfile.exists()) {
	  		configfile.getParentFile().mkdirs();
	       	Practice.get().saveResource("config.yml", false);
	  	}

	   	config = new YamlConfiguration();
	 	try {
	 		config.load(configfile);
	 		return true;
		} catch (IOException | InvalidConfigurationException e1) {
			e1.printStackTrace();
			return false;
		}
	}

}
