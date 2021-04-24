package fr.hunh0w.practice.utils;

import java.util.Base64;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class LocationBuilder {
	
	public static String toChars(Location loc) {
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		return "§8X: §c"+x+"§8, Y: §c"+y+"§8, Z: §c"+z;
	}
	
	public static Location fromBase64(String base64) {
		String[] tab = new String(Base64.getDecoder().decode(base64.getBytes())).split("::");
		double x = Double.parseDouble(tab[1]);
		double y = Double.parseDouble(tab[2]);
		double z = Double.parseDouble(tab[3]);
		float yaw = Float.parseFloat(tab[4]);
		float pitch = Float.parseFloat(tab[5]);
		World w = Bukkit.getWorld(tab[0]);
		if(w == null) return null;
		return new Location(w, x, y, z, yaw, pitch);
	}
	
	public static String toBase64(Location loc) {
		String str = loc.getWorld().getName()+"::"+loc.getX()+"::"+loc.getY()+"::"+loc.getZ()+"::"+loc.getYaw()+"::"+loc.getPitch();
		return Base64.getEncoder().encodeToString(str.getBytes());
	}
	
	public static Location getLocation(FileConfiguration file, String path, boolean directions) {
		double x = file.getDouble(path+".X");
		double y = file.getDouble(path+".Y");
		double z = file.getDouble(path+".Z");
		String w = file.getString(path+".world");
		World world = Bukkit.getWorld(w);
		if(world == null) return null;
		if(directions) {
			float yaw = Float.parseFloat(file.getString(path+".yaw"));
			float pitch = Float.parseFloat(file.getString(path+".pitch"));
			return new Location(world, x, y, z, yaw, pitch);
		}else {
			return new Location(world, x, y, z);
		}
	}
	
	public static void registerLocation(FileConfiguration file, Location loc, String path, boolean directions) {
		file.set(path+".X", loc.getX());
		file.set(path+".Y", loc.getY());
		file.set(path+".Z", loc.getZ());
		file.set(path+".world", loc.getWorld().getName());
		if(directions) {
			file.set(path+".yaw", loc.getYaw());
			file.set(path+".pitch", loc.getPitch());
		}
	}

}
