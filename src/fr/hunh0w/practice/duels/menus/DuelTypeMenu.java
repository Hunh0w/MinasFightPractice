package fr.hunh0w.practice.duels.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.hunh0w.practice.duels.menus.setup.RankedSetup;
import fr.hunh0w.practice.duels.menus.setup.UnRankedSetup;
import fr.hunh0w.practice.utils.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;

public class DuelTypeMenu implements InventoryProvider {
	
	private static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("dueltypechoicemenu")
            .provider(new DuelTypeMenu())
            .size(3, 9)
            .title("§8» §6§lType de Duel")
            .closeable(true)
            .build();
	
	public static void open(Player p) {
		INVENTORY.open(p);
	}

	@Override
	public void init(Player p, InventoryContents inv) {
		
		ItemStack iron_sword = new ItemBuilder(Material.IRON_SWORD, 1).setName("§7§lUnRanked").toItemStack();
		ItemStack diamond_sword = new ItemBuilder(Material.DIAMOND_SWORD, 1).setName("§6§lRanked").toItemStack();
		inv.set(1, 2, ClickableItem.of(iron_sword, e -> {
			UnRankedSetup.open(p);
		}));
		inv.set(1, 6, ClickableItem.of(diamond_sword, e -> {
			RankedSetup.open(p);
		}));
	}

	@Override
	public void update(Player p, InventoryContents inv) {
		
	}

}
