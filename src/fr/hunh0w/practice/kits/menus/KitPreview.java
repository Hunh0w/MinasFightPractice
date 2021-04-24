package fr.hunh0w.practice.kits.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.hunh0w.practice.kits.Kit;
import fr.hunh0w.practice.managers.MenusManager;
import fr.hunh0w.practice.utils.ItemBuilder;

public class KitPreview implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		ItemStack it = e.getCurrentItem();
		if(it == null || it.getType() == Material.AIR) return;
		if(e.getClickedInventory().getName().startsWith("§8> §bKit: §f")) {
			e.setCancelled(true);
			Player p = (Player)e.getWhoClicked();
			if(it.getType() == Material.DARK_OAK_DOOR_ITEM && e.getSlot() == 44) {
				KitsList.INVENTORY.open(p);
				return;
			}
		}
	}
	
	public static Inventory preview(Player p, Kit kit) {
		Inventory inv = Bukkit.createInventory(null, 9*5, "§8> §bKit: §f"+kit.getName());
		ItemBuilder vitre = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte)15).setName("§8Slot inexistant");
		inv.setContents(kit.getItems());
		inv.setItem(36, kit.getArmor()[0]);
		inv.setItem(37, kit.getArmor()[1]);
		inv.setItem(38, kit.getArmor()[2]);
		inv.setItem(39, kit.getArmor()[3]);
		for(int i = 40; i < 44; i++) inv.setItem(i, vitre.toItemStack());
		inv.setItem(44, MenusManager.back_door); 
		
		p.openInventory(inv);
		return inv;
	}

}
