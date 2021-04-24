package fr.hunh0w.practice.mods.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.hunh0w.practice.managers.MenusManager;
import fr.hunh0w.practice.managers.PermissionsManager;
import fr.hunh0w.practice.mods.objects.Mods;
import fr.hunh0w.practice.utils.ItemBuilder;

public class ListMods implements Listener {
	
	@EventHandler
	public void onClickInv(InventoryClickEvent e) {
		if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
		if(e.getClickedInventory() == null) return;
		if(e.getClickedInventory().getName() == null) return;
		if(e.getClickedInventory().getName().equalsIgnoreCase("§5§lMods §f§lDisponibles")) {
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
		Inventory inv = Bukkit.createInventory(null, 27, "§5§lMods §f§lDisponibles");
		
		inv.setItem(26, MenusManager.back_door);
		for(Mods mod : Mods.values()) {
			ItemStack it = mod.getContent().getItem().clone();
			if(!PermissionsManager.hasMod(p, mod)) {
				ItemBuilder ib = new ItemBuilder(it)
						.setLoreLine(4, "§8» §cVous n'avez pas ce Mod")
						.setType(Material.BARRIER);
				it = ib.toItemStack();
			}
			inv.addItem(it);
		}
		
		p.openInventory(inv);
	}

}
