package fr.hunh0w.practice.arenas.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.hunh0w.practice.arenas.ArenasManager;
import fr.hunh0w.practice.arenas.objects.Arena;
import fr.hunh0w.practice.managers.MenusManager;
import fr.hunh0w.practice.utils.ItemBuilder;

public class ListArenas implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
		if(e.getClickedInventory() == null) return;
		if(e.getClickedInventory().getName() == null) return;
		if(e.getClickedInventory().getName().equalsIgnoreCase("§e§lArènes")) {
			e.setCancelled(true);
			ItemStack it = e.getCurrentItem();
			Player p = (Player)e.getWhoClicked();
			if(it.getType() == Material.DARK_OAK_DOOR_ITEM) {
				p.closeInventory();
				return;
			}
		}
	}
	
	
	public static void open(Player p) {
		Inventory inv = Bukkit.createInventory(null, 27, "§e§lArènes");
		
		inv.setItem(26, MenusManager.back_door);
		if(ArenasManager.getArenas().isEmpty()) {
			ItemBuilder item = new ItemBuilder(Material.BARRIER, 1).setName("§c§lAucune Arène(s)");
			inv.addItem(item.toItemStack());
		}else {
			for(Arena arena : ArenasManager.getArenas()) {
				ItemBuilder it = new ItemBuilder(Material.GRASS, 1)
						.setName("§8» §a"+arena.getName());
				inv.addItem(it.toItemStack());
			}
		}
		p.openInventory(inv);
	}

}
