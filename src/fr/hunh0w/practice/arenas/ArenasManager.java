package fr.hunh0w.practice.arenas;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import fr.hunh0w.practice.Practice;
import fr.hunh0w.practice.arenas.objects.Arena;
import fr.hunh0w.practice.managers.GlobalsManager;
import fr.hunh0w.practice.sql.DatabaseManager;
import fr.hunh0w.practice.utils.LocationBuilder;
import fr.hunh0w.practice.utils.WorldeditManager;

public class ArenasManager {
	
	private static ArrayList<Arena> arenas = new ArrayList<>();
	public static ArrayList<Arena> getArenas(){return arenas;}
	
	public static boolean ArenaExists(String name) {
		for(Arena arene : arenas) {
			if(arene.getName().equalsIgnoreCase(name)) return true;
		}
		return false;
	}
	
	public static void removeArena(String name) {
		List<Arena> list = new ArrayList<>();
		for(Arena a : arenas) {
			if(a.getName().equalsIgnoreCase(name)) list.add(a);
		}
		for(Arena a : list) {
			while(arenas.contains(a))
				arenas.remove(a);
			File f = new File(GlobalsManager.arenasfolder, a.getName()+".schematic");
			if(f.exists()) f.delete();
		}
		list.clear();
		try {
			Connection con = DatabaseManager.PRACTICE.getDatabase().getConnection();
			PreparedStatement ps = con.prepareStatement("DELETE FROM arenas WHERE lower(name)=lower(?)");
			ps.setString(1, name);
			ps.executeUpdate();
			ps.close();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void registerArena(Arena arena) {
		try {
			Connection con = DatabaseManager.PRACTICE.getDatabase().getConnection();
			PreparedStatement ps = con.prepareStatement("INSERT INTO arenas(name, location1, location2, spawn1, spawn2, spawnspec) VALUES(?, ?, ?, ?, ?, ?)");
			ps.setString(1, arena.getName());
			ps.setString(2, LocationBuilder.toBase64(arena.getLocation1()));
			ps.setString(3, LocationBuilder.toBase64(arena.getLocation2()));
			ps.setString(4, LocationBuilder.toBase64(arena.getSpawn1()));
			ps.setString(5, LocationBuilder.toBase64(arena.getSpawn2()));
			ps.setString(6, LocationBuilder.toBase64(arena.getSpawnspec()));
			ps.executeUpdate();
			arenas.add(arena);
			ps.close();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		WorldeditManager.saveSchematic(arena);
	}
	
	public static void loadAll() {
		loadDirs();
		arenas.clear();
		try {
			Connection con = DatabaseManager.PRACTICE.getDatabase().getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM arenas");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String name = rs.getString("name");
				Location spawn1 = LocationBuilder.fromBase64(rs.getString("spawn1"));
				Location spawn2 = LocationBuilder.fromBase64(rs.getString("spawn2"));
				Location loc1 = LocationBuilder.fromBase64(rs.getString("location1"));
				Location loc2 = LocationBuilder.fromBase64(rs.getString("location2"));
				Location spawnspec = LocationBuilder.fromBase64(rs.getString("spawnspec"));
				Arena arena = new Arena(loc1, loc2, spawn1, spawn2, spawnspec);
				arena.setName(name);
				arenas.add(arena);
			}
			ps.close();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void loadDirs() {
		File datafolder = Practice.get().getDataFolder();
		File arenasfolder = new File(datafolder, "arenes");
		if(!arenasfolder.exists()) arenasfolder.mkdirs();
		GlobalsManager.arenasfolder = arenasfolder;
	}


}
