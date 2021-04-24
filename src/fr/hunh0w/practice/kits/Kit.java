package fr.hunh0w.practice.kits;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.hunh0w.practice.duels.DuelType;

public abstract class Kit {
	
	protected String name;
	protected ItemStack[] armor;
	protected ItemStack[] items;
	
	protected DuelType type;
	
	Kit(){}
	
	Kit(String name, ItemStack[] armor, ItemStack[] items, DuelType type){
		this.name = name;
		this.armor = armor;
		this.items = items;
		this.type = type;
	}
	
	public DuelType getType() {
		return type;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setArmor(ItemStack[] armor) {
		this.armor = armor;
	}

	public void setItems(ItemStack[] items) {
		this.items = items;
	}

	public void setType(DuelType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public ItemStack[] getArmor() {
		return armor;
	}

	public ItemStack[] getItems(){
		return items;
	}

	
	public void GiveTo(Player p) {
		p.getInventory().setHelmet(armor[0]);
		p.getInventory().setChestplate(armor[1]);
		p.getInventory().setLeggings(armor[2]);
		p.getInventory().setBoots(armor[3]);
		for(ItemStack item : items) p.getInventory().addItem(item);
	}

}
