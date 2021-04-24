package fr.hunh0w.practice.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;

import fr.hunh0w.practice.Practice;
import fr.hunh0w.practice.players.PlayerData;

public class PlayersListener implements Listener {
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if(!e.getPlayer().getWorld().getName().equalsIgnoreCase("spawn")) {
			return;
		}
		PlayerData data = Practice.getPlayerData(e.getPlayer());
		if(data.getBuildMode()) return;
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(!(e.getWhoClicked() instanceof Player)) return;
		Player p = (Player)e.getWhoClicked();
		if(!p.getWorld().getName().equalsIgnoreCase("spawn")) return;
		if(e.getInventory().getType() != InventoryType.CHEST && e.getInventory().getType() != InventoryType.ENDER_CHEST) {
			PlayerData data = Practice.getPlayerData(p);
			if(data.getBuildMode()) return;
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onFood(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

}
