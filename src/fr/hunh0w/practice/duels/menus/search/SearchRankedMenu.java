package fr.hunh0w.practice.duels.menus.search;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.hunh0w.practice.Practice;
import fr.hunh0w.practice.duels.Duel;
import fr.hunh0w.practice.duels.DuelType;
import fr.hunh0w.practice.duels.DuelsManager;
import fr.hunh0w.practice.duels.preconfigs.DuelConfig;
import fr.hunh0w.practice.duels.timers.ChooseArenaTask;
import fr.hunh0w.practice.managers.GlobalsManager;
import fr.hunh0w.practice.managers.PermissionsManager;
import fr.hunh0w.practice.mods.objects.Mods;
import fr.hunh0w.practice.utils.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;

public class SearchRankedMenu implements InventoryProvider {
	
	public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("searchrankedmenu")
            .provider(new SearchRankedMenu())
            .size(3, 9)
            .title("§8» §e§lType de Ranked")
            .closeable(false)
            .build();

	@Override
	public void init(Player player, InventoryContents inv) {
		Search search = SearchManager.searchs.get(player.getName().toLowerCase());
		if(search == null) return;

		for(DuelConfig dpc : DuelConfig.values()) {
			if(dpc == DuelConfig.VIDE) continue;
			inv.add(ClickableItem.of(dpc.getPreConfig().getItem(), e -> {
				search.type = dpc.getPreConfig().getType();
				if(search.mods.contains(Mods.WARFARE)) search.type = DuelType.WARFARE;
				if(search.type == null) return;
				inv.inventory().close(player);
				if(DuelsManager.getDuel(player.getName()) != null) {
					player.sendMessage(GlobalsManager.prefix+"§cVous êtes déjà dans un Duel");
					return;
				}
				player.sendMessage("");
				player.sendMessage(GlobalsManager.prefix+"§fRecherche de §cDuel §e§lClassé §6"+dpc.getPreConfig().getType().getName()+" §favec vos paramètres de recherche...");
				
				
				Duel duel = DuelsManager.getDuel(DuelsManager.getEmptyRankedDuels(search), Practice.getPlayerData(player));

				if(duel == null || duel.player2 != null) {
					player.sendMessage(GlobalsManager.prefix+"§7Non trouvé, création d'un nouveau Duel...");
					duel = new Duel(player, true);
					int access = PermissionsManager.countModsAccess(player);
					int i = duel.mods.size();
					for(Mods mod : search.mods) {
						if(access <= i) break;
						if(!PermissionsManager.hasMod(player, mod)) continue;
						duel.mods.add(mod.getContent());
						i++;
					}
					duel.type = search.type;
					DuelConfig.getDuelConfig(duel.type).PreConfigure(duel);
					new ChooseArenaTask(duel);
					ItemBuilder cancelduel = new ItemBuilder(Material.INK_SACK, 1, (byte)1).setName("§c§lAnnuler le Duel")
							.setLore("", "§8§m----------------------------------",
									   "",
									   "    §c§lDuel Destroyer",
									   "",
									   "§8► §7Cliquez pour supprimer votre §c§lDuel",
									   "", 
									   "§8§m----------------------------------", "");
					player.getInventory().setItem(4, cancelduel.toItemStack());
				}else {
					player.sendMessage(GlobalsManager.prefix+"§7Duel trouvé : §c§l"+duel.creator.getName());
					duel.player2 = player;
					DuelsManager.startDuel(duel);
				}
				player.sendMessage("");
				if(SearchManager.searchs.containsKey(player.getName().toLowerCase()))
					SearchManager.searchs.remove(player.getName().toLowerCase());
			}));
		}
		
		inv.set(2, 8, ClickableItem.of(new ItemBuilder(Material.REDSTONE_BLOCK,1).setName("§cAnnuler").toItemStack(), e -> {
			inv.inventory().close(player);
			if(SearchManager.searchs.containsKey(player.getName().toLowerCase()))
				SearchManager.searchs.remove(player.getName().toLowerCase());
		}));
	}

	@Override
	public void update(Player arg0, InventoryContents arg1) {}

}
