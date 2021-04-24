
package fr.hunh0w.practice.kits.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.hunh0w.practice.kits.KitsManager;
import fr.hunh0w.practice.kits.PresetKit;
import fr.hunh0w.practice.managers.MenusManager;
import fr.hunh0w.practice.utils.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;

public class PresetKitsMenu implements InventoryProvider {
	
	public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("presetkits")
            .provider(new PresetKitsMenu())
            .size(3, 9)
            .title("§8> §6§lKits §f§lPré-Définis")
            .build();

	@Override
	public void init(Player player, InventoryContents inv) {
		inv.set(2, 8, ClickableItem.of(MenusManager.back_door, e -> {
			KitsList.INVENTORY.open(player);
		}));
		if(KitsManager.getPresetKits().isEmpty()) {
			ItemBuilder item = new ItemBuilder(Material.BARRIER, 1).setName("§c§lAucun Kit(s)");
			inv.add(ClickableItem.empty(item.toItemStack()));
		}else {
			for(PresetKit kit : KitsManager.getPresetKits()) {
				ItemBuilder item = new ItemBuilder(Material.CHEST)
						.setName("§b§lKit: §7"+kit.getName())
						.setLore("","§8§m---------------------------------", "",
								"§8§l» §7Cliquez pour prévisualiser ce §b§lKit",
								"", "§8§m---------------------------------", "");
				inv.add(ClickableItem.of(item.toItemStack(), e -> {
					KitPreview.preview(player, kit);
				}));
			}
		}
	}

	@Override
	public void update(Player arg0, InventoryContents arg1) {}

}
