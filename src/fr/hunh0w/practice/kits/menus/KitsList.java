package fr.hunh0w.practice.kits.menus;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import fr.hunh0w.practice.managers.MenusManager;
import fr.hunh0w.practice.utils.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;

public class KitsList implements InventoryProvider {
	
	public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("kitslist")
            .provider(new KitsList())
            .size(1, 9)
            .title("§8» §b§lKits")
            .build();

	@Override
	public void init(Player player, InventoryContents inv) {
		ItemBuilder chest = new ItemBuilder(Material.CHEST, 1).setName("§8» §b§lKits §f§lPré-Définis")
				.setLore("","§8§m----------------------------------", "","§8» §7Ceci est une Fonctionnalité §lStandard","","§8§m----------------------------------", "");
		ItemBuilder enderchest = new ItemBuilder(Material.ENDER_CHEST, 1).setName("§8» §b§lKits §6§lPersonnels")
				.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setFlags(ItemFlag.HIDE_ENCHANTS)
				.setLore("","§8§m----------------------------------", "","§8» §7Ceci est une Fonctionnalité §a§lPremium §7!","","§8§m----------------------------------", "");
		inv.set(0, 3, ClickableItem.of(chest.toItemStack(), e -> {
			PresetKitsMenu.INVENTORY.open(player);
		}));
		inv.set(0, 5, ClickableItem.of(enderchest.toItemStack(), e -> {
			MyKitsMenu.INVENTORY.open(player);
		}));
		inv.set(0, 8, ClickableItem.of(MenusManager.back_door, e -> {
			inv.inventory().close(player);
		}));
	}

	@Override
	public void update(Player arg0, InventoryContents arg1) {}

}
