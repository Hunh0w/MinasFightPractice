package fr.hunh0w.practice.duels.menus.setup;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.hunh0w.practice.duels.Duel;
import fr.hunh0w.practice.duels.DuelType;
import fr.hunh0w.practice.duels.preconfigs.DuelConfig;
import fr.hunh0w.practice.utils.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;

public class PreConfigMenu implements InventoryProvider {
	
	public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("preconfigmenu")
            .provider(new PreConfigMenu())
            .size(3, 9)
            .title("§8» §6§lPré-Configurations")
            .closeable(false)
            .build();

	@Override
	public void init(Player p, InventoryContents inv) {
		for(DuelType dpc : DuelType.values()) {
			if(dpc == DuelType.WARFARE || dpc == DuelType.CUSTOM) continue;
			DuelConfig dc = DuelConfig.getDuelConfig(dpc);
			inv.add(ClickableItem.of(dc.getPreConfig().getItem(), e -> {
				Duel duel = DuelSetup.getMakingDuels().get(p.getName().toLowerCase());
				duel.type = dpc;
				dc.PreConfigure(duel);
				DuelSetup.INVENTORY.open(p);
			}));
		}
		inv.set(2, 8, ClickableItem.of(new ItemBuilder(Material.DARK_OAK_DOOR_ITEM,1).setName("§cRetour").toItemStack(), e -> {
			DuelSetup.INVENTORY.open(p);
		}));
	}

	@Override
	public void update(Player p, InventoryContents inv) {}
	
	

}
