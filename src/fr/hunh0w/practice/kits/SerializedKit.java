package fr.hunh0w.practice.kits;

import java.io.Serializable;

import org.bukkit.inventory.ItemStack;

import fr.hunh0w.practice.duels.DuelType;
import fr.hunh0w.practice.managers.CompressManager;

public class SerializedKit implements Serializable {

	private static final long serialVersionUID = 5843815708658396056L;
	
	public String[] armor = new String[4];
	public String[] items = null;
	public int dueltype;
	public String name;
	
	public SerializedKit(String name, DuelType type, ItemStack[] armor, ItemStack[] items) {
		this.name = name;
		this.dueltype = type.getId();
		setArmor(armor);
		setItems(items);
	}
	
	public void setArmor(ItemStack[] items) {
		armor[0] = (items[0] == null?null:CompressManager.serializeItemStack(items[0]));
		armor[1] = (items[1] == null?null:CompressManager.serializeItemStack(items[1]));
		armor[2] = (items[2] == null?null:CompressManager.serializeItemStack(items[2]));
		armor[3] = (items[3] == null?null:CompressManager.serializeItemStack(items[3]));
	}
	
	public void setItems(ItemStack[] list) {
		this.items = new String[list.length];
		for(int i = 0; i < list.length; i++) {
			if(list[i] != null)
				items[i] = CompressManager.serializeItemStack(list[i]);
			else items[i] = null;
		}
	}
	
	public ItemStack[] toItemArmor() {
		ItemStack[] array = new ItemStack[4];
		array[0] = (armor[0] == null?null:CompressManager.deserializeItemStack(armor[0]));
		array[1] = (armor[1] == null?null:CompressManager.deserializeItemStack(armor[1]));
		array[2] = (armor[2] == null?null:CompressManager.deserializeItemStack(armor[2]));
		array[3] = (armor[3] == null?null:CompressManager.deserializeItemStack(armor[3]));
		return array;
	}
	public ItemStack[] toItems(){
		ItemStack[] list = new ItemStack[items.length];
		for(int i = 0; i < items.length; i++) {
			if(items[i] != null && !items[i].isEmpty()) {
				list[i] = CompressManager.deserializeItemStack(items[i]);
			}else items[i] = null;
		}
		return list;
	}
	
	public PresetKit toKit() {
		return new PresetKit(name, toItemArmor(), toItems(), DuelType.getById(dueltype));
	}

}
