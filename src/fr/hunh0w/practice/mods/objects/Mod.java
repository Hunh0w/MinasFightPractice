package fr.hunh0w.practice.mods.objects;

import org.bukkit.inventory.ItemStack;

public abstract class Mod {
	
	protected ItemStack item;
	protected String name;
	
	public abstract void setup();
	
	public ItemStack getItem() {
		return item;
	}
	
	public String getName() {
		return name;
	}

}
