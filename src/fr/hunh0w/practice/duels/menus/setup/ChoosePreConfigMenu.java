package fr.hunh0w.practice.duels.menus.setup;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.hunh0w.practice.duels.Duel;
import fr.hunh0w.practice.duels.preconfigs.DuelConfig;
import fr.hunh0w.practice.duels.timers.ChooseArenaTask;
import fr.hunh0w.practice.utils.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;

public class ChoosePreConfigMenu implements InventoryProvider {
	
	public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("chooserankedpreconfigmenu")
            .provider(new ChoosePreConfigMenu())
            .size(3, 9)
            .title("§8» §b§lPré-Configurations")
            .closeable(false)
            .build();

	@Override
	public void init(Player player, InventoryContents inv) {
		for(DuelConfig dpc : DuelConfig.values()) {
			if(dpc == DuelConfig.VIDE) continue;
			inv.add(ClickableItem.of(dpc.getPreConfig().getItem(), e -> {
				inv.inventory().close(player);
				Duel duel = DuelSetup.getMakingDuels().get(player.getName().toLowerCase());
				duel.type = dpc.getPreConfig().getType();
				dpc.PreConfigure(duel);
				
				if(DuelSetup.getMakingDuels().containsKey(player.getName().toLowerCase()))
					DuelSetup.getMakingDuels().remove(player.getName().toLowerCase());
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
			}));
		}
		inv.set(2, 8, ClickableItem.of(new ItemBuilder(Material.REDSTONE_BLOCK,1).setName("§cAnnuler").toItemStack(), e -> {
			inv.inventory().close(player);
		}));
	}

	@Override
	public void update(Player arg0, InventoryContents arg1) {}

}
