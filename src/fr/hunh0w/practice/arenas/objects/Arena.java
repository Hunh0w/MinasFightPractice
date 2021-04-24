package fr.hunh0w.practice.arenas.objects;

import org.bukkit.Location;

import fr.hunh0w.practice.utils.Cuboid;

public class Arena {
	
	private String name;
	
	private Location spawn1;
	private Location spawn2;
	private Location spawnspec;
	private Location loc1;
	private Location loc2;
	
	public Arena(Location loc1, Location loc2, Location spawn1, Location spawn2, Location spawnspec) {
		this.loc1 = loc1;
		this.loc2 = loc2;
		this.spawn1 = spawn1;
		this.spawn2 = spawn2;
		this.spawnspec = spawnspec;
	}

	public Location getSpawn1() {
		return spawn1;
	}

	public Location getSpawn2() {
		return spawn2;
	}

	public Location getSpawnspec() {
		return spawnspec;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Location getLocation2() {
		return loc2;
	}
	
	public Location getLocation1() {
		return loc1;
	}
	
	public Cuboid getCube() {
		return new Cuboid(loc1, loc2);
	}
	

}
