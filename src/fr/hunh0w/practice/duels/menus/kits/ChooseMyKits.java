package fr.hunh0w.practice.duels.menus.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import fr.hunh0w.practice.Practice;
import fr.hunh0w.practice.duels.Duel;
import fr.hunh0w.practice.duels.DuelType;
import fr.hunh0w.practice.duels.DuelsManager;
import fr.hunh0w.practice.kits.CustomKit;
import fr.hunh0w.practice.kits.KitsManager;
import fr.hunh0w.practice.managers.MenusManager;
import fr.hunh0w.practice.players.PlayerData;
import fr.hunh0w.practice.utils.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;

public class ChooseMyKits implements InventoryProvider {
	
	public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("choosemykits")
            .provider(new ChooseMyKits())
            .size(3, 9)
            .title("§8» §b§lChoisir un Kit Personnel")
            .closeable(false)
            .build();

	@Override
	public void init(Player player, InventoryContents inv) {
		PlayerData data = Practice.getPlayerData(player);
		inv.set(2, 8, ClickableItem.of(MenusManager.back_door, e -> {
			ChooseKitMenu.INVENTORY.open(player);
		}));
		
		if(data.getKits().isEmpty()) {
			ItemBuilder item = new ItemBuilder(Material.BARRIER, 1).setName("§c§lAucun Kit(s)");
			inv.add(ClickableItem.empty(item.toItemStack()));
		}else {
			Duel duel = DuelsManager.getDuel(player.getName());
			for(CustomKit kit : data.getKits()) {
				if(kit.getType() != duel.type && duel.type != DuelType.CUSTOM) continue;
				if(duel.type == DuelType.CUSTOM && kit.getType() == DuelType.WARFARE) continue;
				ItemBuilder item = new ItemBuilder(Material.ENDER_CHEST).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setFlags(ItemFlag.HIDE_ENCHANTS)
						.setName("§b§lKit: §7"+kit.getName())
						.setLore("","§8§m---------------------------------", "",
								"§8§l» §7Cliquez pour choisir ce §b§lKit",
								"", "§8§m---------------------------------", "");
				inv.add(ClickableItem.of(item.toItemStack(), e -> {
					inv.inventory().close(player);
					if(DuelsManager.getDuel(player.getName()) == null) return;
					KitsManager.giveKit(player, kit);
				}));
			}
		}
	}

	@Override
	public void update(Player arg0, InventoryContents arg1) {}

}
