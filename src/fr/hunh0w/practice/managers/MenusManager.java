package fr.hunh0w.practice.managers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.hunh0w.practice.utils.ItemBuilder;

public class MenusManager {
	
	public static final ItemStack back_door = new ItemBuilder(Material.DARK_OAK_DOOR_ITEM, 1).setName("§cRetour").toItemStack(); 
	public static final ItemStack arrow_left = new ItemBuilder(Material.ARROW,1).setName("§f§lPage Précédente").toItemStack();
	public static final ItemStack arrow_right = new ItemBuilder(Material.ARROW,1).setName("§f§lPage Suivante").toItemStack();
	
	/*
	public static void openBackInventory(Player p, Inventory current) {
		PlayerData pd = Practice.getPlayerData(p);
		Inventory re = pd.getBackInv().get(current);
		if(re == null) {
			pd.BackInventoryClear();
			p.closeInventory();
			return;
		}
		p.openInventory(re);
		pd.BackInventoryPut(current, null);
	}
	*/
	
}
