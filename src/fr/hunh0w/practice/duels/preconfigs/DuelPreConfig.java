package fr.hunh0w.practice.duels.preconfigs;

import org.bukkit.inventory.ItemStack;

import fr.hunh0w.practice.duels.Duel;
import fr.hunh0w.practice.duels.DuelType;

public abstract class DuelPreConfig {
	
	protected String name;
	protected DuelType type;
	
	protected ItemStack item;
	
	public DuelPreConfig(DuelType type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public DuelType getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public abstract void PreConfigure(Duel duel);

}
