package fr.hunh0w.practice.kits.menus;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import fr.hunh0w.practice.Practice;
import fr.hunh0w.practice.kits.CustomKit;
import fr.hunh0w.practice.managers.MenusManager;
import fr.hunh0w.practice.players.PlayerData;
import fr.hunh0w.practice.utils.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;

public class MyKitsMenu implements InventoryProvider {
	
	public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("mykitspersonal")
            .provider(new MyKitsMenu())
            .size(3, 9)
            .title("§8» §6§lMes Kits")
            .build();

	@Override
	public void init(Player player, InventoryContents inv) {
		PlayerData data = Practice.getPlayerData(player);
		inv.set(2, 8, ClickableItem.of(MenusManager.back_door, e -> {
			KitsList.INVENTORY.open(player);
		}));
		
		if(data.getKits().isEmpty()) {
			ItemBuilder item = new ItemBuilder(Material.BARRIER, 1).setName("§c§lAucun Kit(s)");
			inv.add(ClickableItem.empty(item.toItemStack()));
		}else {
			for(CustomKit kit : data.getKits()) {
				ItemBuilder item = new ItemBuilder(Material.ENDER_CHEST).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setFlags(ItemFlag.HIDE_ENCHANTS)
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
