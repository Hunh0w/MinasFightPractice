package fr.hunh0w.practice.duels.menus.search;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import fr.hunh0w.practice.managers.MenusManager;
import fr.hunh0w.practice.mods.objects.Mods;
import fr.hunh0w.practice.utils.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;

public class SearchModMenu implements InventoryProvider {
	
	public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("searchmodmenu")
            .provider(new SearchModMenu())
            .size(3, 9)
            .title("§8» §7Recherche de §5§lMods")
            .closeable(false)
            .build();

	@Override
	public void init(Player player, InventoryContents inv) {
		Search srch = SearchManager.searchs.get(player.getName().toLowerCase());
		if(srch == null) {
			inv.inventory().close(player);
			return;
		}
		for(Mods mod : Mods.values()) {
			ItemBuilder it = new ItemBuilder(mod.getContent().getItem().clone());
			if(srch.mods.contains(mod)) it.setLoreLine(4, "§8» §a§lSélectionné").addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1).setFlags(ItemFlag.HIDE_ENCHANTS);
			else it.setLoreLine(4, "§8» §c§lNon Sélectionné");
			inv.add(ClickableItem.of(it.toItemStack(), e -> {
				ItemBuilder item = new ItemBuilder(mod.getContent().getItem().clone());;
				if(srch.mods.contains(mod)) {
					item.setLoreLine(4, "§8» §c§lNon Sélectionné");
					while(srch.mods.contains(mod)) srch.mods.remove(mod);
				}else {
					item.setLoreLine(4, "§8» §a§lSélectionné")
						.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1)
						.setFlags(ItemFlag.HIDE_ENCHANTS);
					srch.mods.add(mod);
				}
				e.setCurrentItem(item.toItemStack());
			}));
		}
		inv.set(2, 8, ClickableItem.of(MenusManager.back_door, e -> {
			if(srch.ranked)
				SearchRankedMenu.INVENTORY.open(player);
			else 
				SearchUnRankedMenu.INVENTORY.open(player);
		}));
	}

	@Override
	public void update(Player arg0, InventoryContents arg1) {}

}
