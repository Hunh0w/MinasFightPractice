package fr.hunh0w.practice.kits.setup.presetkits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.hunh0w.practice.duels.DuelType;
import fr.hunh0w.practice.kits.KitsManager;
import fr.hunh0w.practice.kits.PresetKit;
import fr.hunh0w.practice.managers.GlobalsManager;
import fr.hunh0w.practice.utils.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;

public class ChoosePresetKitType implements InventoryProvider {
	
	public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("choosepresetkittype")
            .provider(new ChoosePresetKitType())
            .size(3, 9)
            .title("§8» §bType de Kit")
            .closeable(false)
            .build();

	@Override
	public void init(Player player, InventoryContents inv) {
		
		PresetKit kit = PresetKitSetup.kits.get(player.getName().toLowerCase());
		if(kit == null) {
			inv.inventory().close(player);
			return;
		}
		
		for(DuelType type : DuelType.values()) {
			ItemBuilder it = new ItemBuilder(Material.CHEST,1).setName("§8» §f§l"+type.getName());
			inv.add(ClickableItem.of(it.toItemStack(), e -> {
				inv.inventory().close(player);
				if(kit == null || kit.getName() == null || kit.getName().isEmpty()) return;
				kit.setType(type);
				ItemStack[] armor = new ItemStack[4];
				armor[0] = player.getInventory().getHelmet();
				armor[1] = player.getInventory().getChestplate();
				armor[2] = player.getInventory().getLeggings();
				armor[3] = player.getInventory().getBoots();
				ItemStack[] items = player.getInventory().getContents();
				kit.setItems(items);
				kit.setArmor(armor);
				KitsManager.getPresetKits().add(kit);
				if(PresetKitSetup.kits.containsKey(player.getName().toLowerCase()))
					PresetKitSetup.kits.remove(player.getName().toLowerCase());
				PresetKit.savePresetKits();
				player.sendMessage(GlobalsManager.prefix+"§fVous venez de §acréer §fle §6Kit Pré-Défini §f: §8'§e"+kit.getName()+"§8'");
			}));
		}
		
		inv.set(2, 8, ClickableItem.of(new ItemBuilder(Material.REDSTONE_BLOCK,1).setName("§cAnnuler").toItemStack(), e -> {
			inv.inventory().close(player);
			if(PresetKitSetup.kits.containsKey(player.getName().toLowerCase()))
				PresetKitSetup.kits.remove(player.getName().toLowerCase());
		}));
	}

	@Override
	public void update(Player arg0, InventoryContents arg1) {}
	
	

}
