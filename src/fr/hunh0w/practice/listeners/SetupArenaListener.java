package fr.hunh0w.practice.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.hunh0w.practice.Practice;
import fr.hunh0w.practice.arenas.objects.ArenaSetup;
import fr.hunh0w.practice.managers.GlobalsManager;

public class SetupArenaListener implements Listener {
	
	/* 
	 *  ARENAS LOC1 & LOC2 SETUP
	 *  */
	@EventHandler
	public void PlayerInteract(PlayerInteractEvent e) {
		if(e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.LEFT_CLICK_BLOCK) return;
		if(e.getClickedBlock() == null) return;
		Player p = e.getPlayer();
		ItemStack it = p.getItemInHand();
		if(it == null || it.getType() == Material.AIR) return;
		if(it.getType() == Material.FEATHER) {
			ItemMeta meta = it.getItemMeta();
			if(meta == null) return;
			if(meta.getDisplayName() == null) return;
			if(!meta.getDisplayName().equalsIgnoreCase("ARENACREATOR")) return;
			e.setCancelled(true);
			ArenaSetup setup = Practice.getSetup(p);
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				setup.location1 = e.getClickedBlock().getLocation();
				p.sendMessage(GlobalsManager.prefix+"§aPoint 1 §fdéfini");
			}else if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
				setup.location2 = e.getClickedBlock().getLocation();
				p.sendMessage(GlobalsManager.prefix+"§aPoint 2 §fdéfini");
			}
		}
	}
	

}
