package fr.hunh0w.practice.arenas.objects;

import org.bukkit.Location;

public class ArenaSetup {
	
	public Location spawn1 = null;
	public Location spawn2 = null;
	public Location spawnspec = null;
	
	public Location location1 = null;
	public Location location2 = null;
	
	public boolean ready() {
		if(spawn1 == null || spawn2 == null || spawnspec == null)
			return false;
		if(location1 == null || location2 == null)
			return false;
		return true;
	}
	
	public void clear() {
		location1 = null;
		location2 = null;
		spawn1 = null;
		spawn2 = null;
		spawnspec = null;
	}

	public Arena toArena(String name) {
		if(!ready()) return null;
		Arena arena = new Arena(location1, location2, spawn1, spawn2, spawnspec);
		arena.setName(name);
		return arena;
	}
	

}
