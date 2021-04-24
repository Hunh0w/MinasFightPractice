package fr.hunh0w.practice.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import fr.hunh0w.practice.Practice;
import fr.hunh0w.practice.duels.DuelsManager;
import fr.hunh0w.practice.duels.menus.DuelTypeMenu;
import fr.hunh0w.practice.duels.menus.ListUnRankeds;
import fr.hunh0w.practice.duels.menus.search.Search;
import fr.hunh0w.practice.duels.menus.search.SearchManager;
import fr.hunh0w.practice.duels.menus.search.SearchRankedMenu;
import fr.hunh0w.practice.duels.menus.search.SearchUnRankedMenu;
import fr.hunh0w.practice.kits.menus.KitsList;
import fr.hunh0w.practice.managers.GlobalsManager;
import fr.hunh0w.practice.mods.menus.ListMods;
import fr.hunh0w.practice.mods.objects.Mods;
import fr.hunh0w.practice.players.PlayerData;
import fr.hunh0w.practice.players.PlayerStatus;
import fr.hunh0w.practice.utils.ItemBuilder;

public class SpawnInteractsListener implements Listener {
	
	@EventHandler
	public void onItemUse(PlayerItemDamageEvent e) {
		if(e.getPlayer().getWorld().getName().equalsIgnoreCase("spawn"))
			e.setCancelled(true);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		PlayerData pd = Practice.getPlayerData(p);
		ItemStack it = e.getItem();
		if(it == null || it.getType() == Material.AIR) return;
		if(pd.getState() == PlayerStatus.NONE) {
			if(it.getType() == Material.CHEST) {
				e.setCancelled(true);
				pd.BackInventoryClear();
				KitsList.INVENTORY.open(p);
				return;
			}
			if(it.getType() == Material.NETHER_STAR) {
				e.setCancelled(true);
				pd.BackInventoryClear();
				ListMods.open(p);
				return;
			}
			if(it.getType() == Material.IRON_SWORD) {
				e.setCancelled(true);
				if(DuelsManager.getDuel(p.getName()) != null) return;
				pd.BackInventoryClear();
				if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
					ListUnRankeds.open(p);
				}else {
					Search search = new Search();
					search.ranked = false;
					search.mods.add(Mods.NORMAL);
					SearchManager.searchs.put(p.getName().toLowerCase(), search);
					SearchUnRankedMenu.INVENTORY.open(p);
				}
				return;
			}
			if(it.getType() == Material.DIAMOND_SWORD) {
				e.setCancelled(true);
				if(DuelsManager.getDuel(p.getName()) != null) return;
				pd.BackInventoryClear();
				Search search = new Search();
				search.ranked = true;
				search.mods.add(Mods.NORMAL);
				SearchManager.searchs.put(p.getName().toLowerCase(), search);
				SearchRankedMenu.INVENTORY.open(p);
				return;
			}
			if(it.getType() == Material.INK_SACK) {
				e.setCancelled(true);
				if(it.getData().getData() == (byte)10) {
					if(DuelsManager.getDuel(p.getName()) != null) {
						p.sendMessage(GlobalsManager.prefix+"§cVous avez déjà créé un Duel");
						return;
					}
					pd.BackInventoryClear();
					DuelTypeMenu.open(p);
					return;
				}else if(it.getData().getData() == (byte)1) {
					if(pd.arenafindtask == null) {
						p.sendMessage(GlobalsManager.prefix+"§cVous n'avez pas créé de Duel");
					}else {
						DuelsManager.removeAll(p.getName());
						ItemBuilder duelcreator = new ItemBuilder(Material.INK_SACK, 1, (byte)10).setName("§8➤ §a§lCréer un §c§lDuel")
								.setLore("", "§8§m----------------------------------",
										   "",
										   "    §a§lDuel Creator",
										   "",
										   "§8► §7Cliquez pour créer votre propre §c§lDuel §7!",
										   "", 
										   "§8§m----------------------------------", "");
						p.getInventory().setItem(4, duelcreator.toItemStack());

						pd.arenafindtask.stop();
						pd.arenafindtask = null;
						
						p.sendMessage(GlobalsManager.prefix+"§fVotre §cDuel §fa été §csupprimé");
					}
				}
			}
		}
	}

}
