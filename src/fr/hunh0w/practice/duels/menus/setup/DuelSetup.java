package fr.hunh0w.practice.duels.menus.setup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import fr.hunh0w.practice.duels.Duel;
import fr.hunh0w.practice.duels.DuelType;
import fr.hunh0w.practice.duels.menus.mods.ChooseModsMenu;
import fr.hunh0w.practice.duels.timers.ChooseArenaTask;
import fr.hunh0w.practice.mods.objects.Mod;
import fr.hunh0w.practice.mods.objects.Mods;
import fr.hunh0w.practice.utils.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;

public class DuelSetup implements InventoryProvider{
	
	private static HashMap<String, Duel> makingDuels = new HashMap<>();
	public static HashMap<String, Duel> getMakingDuels(){ return makingDuels;}
	
	
	
	public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("duelcreatormenu")
            .provider(new DuelSetup())
            .size(6, 9)
            .title("§8» §6§lDuel Creator")
            .closeable(false)
            .build();
	
	@Override
	public void init(Player p, InventoryContents inv) {
		Duel duel = makingDuels.get(p.getName().toLowerCase());
		ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte)15).setName("§8§kBienvu").toItemStack();
		inv.fillRow(0, ClickableItem.of(glass, e -> {}));
		inv.fillColumn(3, ClickableItem.of(glass, e -> {}));
		inv.fillRect(0, 0, 5, 6, ClickableItem.of(glass, e -> {}));
		
		inv.set(1, 8, ClickableItem.of(new ItemBuilder(Material.REDSTONE_BLOCK, 1).setName("§c§lAnnuler").toItemStack(), e -> {
			inv.inventory().close(p);
			if(makingDuels.containsKey(p.getName().toLowerCase()))
				makingDuels.remove(p.getName().toLowerCase());
		}));
		inv.set(3, 8, ClickableItem.of(new ItemBuilder(Material.SLIME_BLOCK, 1).setName("§a§lCréer").toItemStack(), e -> {
			inv.inventory().close(p);
			if(makingDuels.containsKey(p.getName().toLowerCase()))
				makingDuels.remove(p.getName().toLowerCase());
			if(duel.type != DuelType.WARFARE && duel.mods.contains(Mods.WARFARE.getContent())) duel.type = DuelType.WARFARE;
			new ChooseArenaTask(duel);
			ItemBuilder cancelduel = new ItemBuilder(Material.INK_SACK, 1, (byte)1).setName("§c§lAnnuler le Duel")
			.setLore("", "§8§m----------------------------------",
					   "",
					   "    §c§lDuel Destroyer",
					   "",
					   "§8► §7Cliquez pour supprimer votre §c§lDuel",
					   "", 
					   "§8§m----------------------------------", "");
			p.getInventory().setItem(4, cancelduel.toItemStack());
		}));
		
		List<String> mod_lores = new ArrayList<>();
		mod_lores.add("");
		for(Mod mod : duel.mods)
			mod_lores.add("§8» "+(mod == Mods.NORMAL.getContent()?"§7":"§5§l")+mod.getName()); 
		mod_lores.add("");
		inv.set(5, 8, ClickableItem.of(new ItemBuilder(Material.ENCHANTED_BOOK, 1).setName("§5§lMods").setLore(mod_lores).toItemStack(), e -> {
			ChooseModsMenu.INVENTORY.open(p);
		}));
		
		if(duel.type == DuelType.WARFARE || duel.mods.contains(Mods.WARFARE.getContent())) {
			inv.set(1, 4, ClickableItem.empty(new ItemBuilder(Material.DIAMOND_HOE,1).setName("§e§lDuel §6§lWarfare").setLore("", "§7Désactivez le Mod §5Warfare §7pour configurer votre Duel", "").addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setFlags(ItemFlag.HIDE_ENCHANTS).toItemStack()));
			return;
		}
		
		inv.set(4, 4, ClickableItem.of(new ItemBuilder(Material.ARROW).setName("§6§lPré-Configurations").setLore("", "§7Cliquez pour choisir une pré-configuration", "").addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setFlags(ItemFlag.HIDE_ENCHANTS).toItemStack(), e -> {
			PreConfigMenu.INVENTORY.open(p);
		}));
		
		generateButtons(p, inv, duel);
	}
	
	public static void generateButtons(Player p, InventoryContents inv, Duel duel) {
		inv.set(1, 1, ClickableItem.of(new ItemBuilder((duel.canne?Material.FISHING_ROD:Material.BARRIER), 1).setName("§f§lCanne à Pêche : "+(duel.canne?"§a§lActivé":"§c§lDésactivé")).setLore("", "§7Cliquez pour §aActiver§8/§cDésactiver", "").toItemStack(), e -> {
			if(duel.type == DuelType.WARFARE) return;
			duel.type = DuelType.CUSTOM;
			duel.canne = !duel.canne;
			ItemBuilder item = new ItemBuilder(e.getCurrentItem()).setName("§f§lCanne à Pêche : "+(duel.canne?"§a§lActivé":"§c§lDésactivé"));
			if(!duel.canne) item.setType(Material.BARRIER);
			else item.setType(Material.FISHING_ROD);
			e.setCurrentItem(item.toItemStack());
		}));
		inv.set(1, 2, ClickableItem.of(new ItemBuilder((duel.arc?Material.BOW:Material.BARRIER), 1).setName("§f§lArc : "+(duel.arc?"§a§lActivé":"§c§lDésactivé")).setLore("", "§7Cliquez pour §aActiver§8/§cDésactiver", "").toItemStack(), e -> {
			if(duel.type == DuelType.WARFARE) return;
			duel.type = DuelType.CUSTOM;
			duel.arc = !duel.arc;
			ItemBuilder item = new ItemBuilder(e.getCurrentItem()).setName("§f§lArc : "+(duel.arc?"§a§lActivé":"§c§lDésactivé"));
			if(!duel.arc) item.setType(Material.BARRIER);
			else item.setType(Material.BOW);
			e.setCurrentItem(item.toItemStack());
		}));
		inv.set(4, 1, ClickableItem.of(GetSceaux(duel.sceaux).toItemStack(), e -> {
			if(duel.type == DuelType.WARFARE) return;
			duel.type = DuelType.CUSTOM;
			if(duel.sceaux >= 3) duel.sceaux = 0;
			else duel.sceaux = duel.sceaux + 1;
			e.setCurrentItem(GetSceaux(duel.sceaux).toItemStack());
		}));
		inv.set(4, 2, ClickableItem.of(new ItemBuilder((duel.build?Material.BRICK:Material.BARRIER), 1).setName("§f§lBuild : "+(duel.build?"§a§lActivé":"§c§lDésactivé")).setLore("", "§7Cliquez pour §aActiver§8/§cDésactiver", "").toItemStack(), e -> {
			if(duel.type == DuelType.WARFARE) return;
			duel.type = DuelType.CUSTOM;
			duel.build = !duel.build;
			ItemBuilder item = new ItemBuilder(e.getCurrentItem()).setName("§f§lBuild : "+(duel.build?"§a§lActivé":"§c§lDésactivé"));
			if(!duel.build) item.setType(Material.BARRIER);
			else item.setType(Material.BRICK);
			e.setCurrentItem(item.toItemStack());
		}));
		
		inv.set(2, 1, ClickableItem.of(new ItemBuilder((duel.goodpotions?Material.POTION:Material.BARRIER), 1).setDurability((short)(duel.goodpotions?16421:0)).setName("§f§lPotions Bénéfiques : "+(duel.goodpotions?"§a§lActivé":"§c§lDésactivé")).setLore("", "§7Cliquez pour §aActiver§8/§cDésactiver", "").toItemStack(), e -> {
			if(duel.type == DuelType.WARFARE) return;
			duel.type = DuelType.CUSTOM;
			duel.goodpotions = !duel.goodpotions;
			ItemBuilder item = new ItemBuilder(e.getCurrentItem()).setName("§f§lPotions Bénéfiques : "+(duel.goodpotions?"§a§lActivé":"§c§lDésactivé"));
			if(!duel.goodpotions) item.setType(Material.BARRIER);
			else item.setType(Material.POTION).setDurability((short)16421);
			e.setCurrentItem(item.toItemStack());
		}));
		inv.set(2, 2, ClickableItem.of(new ItemBuilder((duel.badpotions?Material.POTION:Material.BARRIER), 1).setDurability((short)(duel.badpotions?16420:0)).setName("§f§lPotions Malveillantes : "+(duel.badpotions?"§a§lActivé":"§c§lDésactivé")).setLore("", "§7Cliquez pour §aActiver§8/§cDésactiver", "").toItemStack(), e -> {
			if(duel.type == DuelType.WARFARE) return;
			duel.type = DuelType.CUSTOM;
			duel.badpotions = !duel.badpotions;
			ItemBuilder item = new ItemBuilder(e.getCurrentItem()).setName("§f§lPotions Malveillantes : "+(duel.badpotions?"§a§lActivé":"§c§lDésactivé"));
			if(!duel.badpotions) item.setType(Material.BARRIER);
			else item.setType(Material.POTION).setDurability((short)16420);
			e.setCurrentItem(item.toItemStack());
		}));
		
		inv.set(3, 1, ClickableItem.of(GetGapple(duel.gapple, false).toItemStack(), e -> {
			if(duel.type == DuelType.WARFARE) return;
			duel.type = DuelType.CUSTOM;
			if(e.isRightClick()) {
				if(duel.gapple <= 0) duel.gapple = 1;
				else if(duel.gapple*2 >= 320) duel.gapple = 0;
				else if(duel.gapple >= 10) duel.gapple = (int) Math.round(duel.gapple*(1.3));
				else duel.gapple = duel.gapple*2;
			}else {
				if(duel.gapple >= 320) duel.gapple = 0;
				else duel.gapple = duel.gapple + 1;
			}
			e.setCurrentItem(GetGapple(duel.gapple, false).toItemStack());
		}));
		
		inv.set(3, 2, ClickableItem.of(GetGapple(duel.gapple_cheat, true).toItemStack(), e -> {
			if(duel.type == DuelType.WARFARE) return;
			duel.type = DuelType.CUSTOM;
			if(e.isRightClick()) {
				if(duel.gapple_cheat <= 0) duel.gapple_cheat = 1;
				else if(duel.gapple_cheat*2 >= 320) duel.gapple_cheat = 0;
				else if(duel.gapple_cheat >= 10) duel.gapple_cheat = (int) Math.round(duel.gapple_cheat*(1.3));
				else duel.gapple_cheat = duel.gapple_cheat*2;
			}else {
				if(duel.gapple_cheat >= 320) duel.gapple_cheat = 0;
				else duel.gapple_cheat = duel.gapple_cheat + 1;
			}
			e.setCurrentItem(GetGapple(duel.gapple_cheat, true).toItemStack());
		}));		
		
	}
	
	private static ItemBuilder GetSceaux(int sceaux) {
		ItemBuilder item = new ItemBuilder(Material.BUCKET);
		item.clearEnchants();
		item.setLore("", "§7Cliquez pour §6Configurer", "");
		if(sceaux == 1) item.setType(Material.WATER_BUCKET).setName("§f§lSceaux : §b§lEau");
		else if(sceaux == 2) item.setType(Material.LAVA_BUCKET).setName("§f§lSceaux : §6§lLave");
		else if(sceaux == 3) item.setType(Material.MILK_BUCKET).setName("§f§lSceaux : §b§lEau §8& §6§lLave").addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setFlags(ItemFlag.HIDE_ENCHANTS);
		else item.setType(Material.BARRIER).setName("§f§lSceaux : §c§lDésactivé");
		return item;
	}
	
	private static ItemBuilder GetGapple(int gapples, boolean cheat) {
		ItemBuilder item = new ItemBuilder(Material.GOLDEN_APPLE);
		item.setLore("", "§7Cliquez pour changer la §6Quantité", "");
		if(gapples <= 0) item.setName("§f§lGapple"+(cheat?" Cheat":"")+" : §c§lDésactivé").setType(Material.BARRIER);
		else {
			item.setName("§f§lGapples"+(cheat?" Cheat":"")+" : §e"+gapples).setType(Material.GOLDEN_APPLE);
			if(cheat) item.setDurability((short)1);
		}
		return item;
	}


	@Override
	public void update(Player arg0, InventoryContents arg1) {}

}
