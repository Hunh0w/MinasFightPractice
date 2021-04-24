package fr.hunh0w.practice.duels.menus.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import fr.hunh0w.practice.utils.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;

public class ChooseKitMenu implements InventoryProvider {
	
	public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("choosekits")
            .provider(new ChooseKitMenu())
            .size(1, 9)
            .title("�8� �b�lChoisir un Kit")
            .closeable(false)
            .build();

	@Override
	public void init(Player player, InventoryContents inv) {
		ItemBuilder chest = new ItemBuilder(Material.CHEST, 1).setName("�8� �b�lKits �f�lPr�-D�finis")
				.setLore("","�8�m----------------------------------", "","�8� �7Ceci est une Fonctionnalit� �lStandard","","�8�m----------------------------------", "");
		
		ItemBuilder enderchest = new ItemBuilder(Material.ENDER_CHEST, 1).setName("�8� �b�lKits �6�lPersonnels")
				.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setFlags(ItemFlag.HIDE_ENCHANTS)
				.setLore("","�8�m----------------------------------", "","�8� �7Ceci est une Fonctionnalit� �a�lPremium �7!","","�8�m----------------------------------", "");
		inv.set(0, 3, ClickableItem.of(chest.toItemStack(), e -> {
			ChoosePresetKits.INVENTORY.open(player);
		}));
		inv.set(0, 5, ClickableItem.of(enderchest.toItemStack(), e -> {
			ChooseMyKits.INVENTORY.open(player);
		}));
	}

	@Override
	public void update(Player arg0, InventoryContents arg1) {}

}
