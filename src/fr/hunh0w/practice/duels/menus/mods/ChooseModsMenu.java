package fr.hunh0w.practice.duels.menus.mods;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import fr.hunh0w.practice.duels.Duel;
import fr.hunh0w.practice.duels.DuelType;
import fr.hunh0w.practice.duels.menus.setup.DuelSetup;
import fr.hunh0w.practice.duels.preconfigs.DuelConfig;
import fr.hunh0w.practice.managers.GlobalsManager;
import fr.hunh0w.practice.managers.MenusManager;
import fr.hunh0w.practice.managers.PermissionsManager;
import fr.hunh0w.practice.mods.objects.Mods;
import fr.hunh0w.practice.utils.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;

public class ChooseModsMenu implements InventoryProvider {
	
	public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("duelchoosemodsmenu")
            .provider(new ChooseModsMenu())
            .size(3, 9)
            .title("§8» §5§lMods")
            .closeable(false)
            .build();

	@Override
	public void init(Player player, InventoryContents inv) {
		Duel duel = DuelSetup.getMakingDuels().get(player.getName().toLowerCase());
		if(duel == null) {
			player.sendMessage(GlobalsManager.prefix+"§cErreur code : "+fr.hunh0w.practice.enums.Error.MAKINGDUEL_NOTFOUND.getCode());
			inv.inventory().close(player);
			return;
		}
		
		inv.set(2, 8, ClickableItem.of(MenusManager.back_door, e -> {
			DuelSetup.INVENTORY.open(player);
		}));
		
		for(Mods mod : Mods.values()) {
			if(mod == Mods.NORMAL) continue;
			ClickableItem citem = null;
			ItemBuilder item = new ItemBuilder(mod.getContent().getItem().clone());
			item.setLoreLine(7, "§8§m"+GlobalsManager.linespace);
			item.setLoreLine(6, "");
			item.addLoreLine("");
			boolean selected = duel.mods.contains(mod.getContent());
			int access = PermissionsManager.countModsAccess(player);
			if(selected) {
				item.setLoreLine(5, "§8» §a§lSélectionné");
				item.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
			}else item.setLoreLine(5, "§8» §c§lNon Sélectionné");
			
			if(selected) {
				citem = ClickableItem.of(item.toItemStack(), e -> {
						while(duel.mods.contains(mod.getContent()))
							duel.mods.remove(mod.getContent());
						if(mod == Mods.WARFARE) duel.type = DuelType.CUSTOM;
						INVENTORY.open(player);
				});
			}else {
				if(!PermissionsManager.hasMod(player, mod)) {
					item.setLoreLine(4, "§8» §cVous n'avez pas ce Mod")
							.setType(Material.BARRIER);
					citem = ClickableItem.empty(item.toItemStack());
				}else {
					if(access <= duel.mods.size()-1) {
						citem = ClickableItem.empty(item.toItemStack());
					}else {
						citem = ClickableItem.of(item.toItemStack(), e -> {
							if(!duel.mods.contains(mod.getContent()))
								duel.mods.add(mod.getContent());
							if(mod == Mods.WARFARE) {
								duel.type = DuelType.WARFARE;
								DuelConfig.VIDE.PreConfigure(duel);
							}
							INVENTORY.open(player);
						});
					}
				}
			}
			if(citem != null)
				inv.add(citem);
		}
		
	}

	@Override
	public void update(Player arg0, InventoryContents arg1) {}

}
