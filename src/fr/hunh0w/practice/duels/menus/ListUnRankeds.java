package fr.hunh0w.practice.duels.menus;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.hunh0w.practice.duels.Duel;
import fr.hunh0w.practice.duels.DuelsManager;
import fr.hunh0w.practice.managers.GlobalsManager;
import fr.hunh0w.practice.managers.MenusManager;
import fr.hunh0w.practice.mods.objects.Mod;
import fr.hunh0w.practice.utils.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;

public class ListUnRankeds implements InventoryProvider {
	
	private static ClickableItem buildItem(Duel duel, InventoryContents inv) {
		String sceauxstr = "§c§lDésactivé";
		if(duel.sceaux == 1) sceauxstr = "§b§lEau";
		else if(duel.sceaux == 2) sceauxstr = "§6§lLave";
		else if(duel.sceaux == 3) sceauxstr = "§b§lEau §8& §6§lLave";
		ItemBuilder item = new ItemBuilder(Material.IRON_SWORD,1)
				.setName("§c§lDuel §f§l: "+duel.creator.getName())
				.setLore(
						"",
						"§8§m"+GlobalsManager.linespace,
						"",
						"§8§l» §aArène : §e"+duel.arena.getName(),
						"§8§l» §6Type de Duel : §e"+duel.type.getName(),
						"",
						"§8» §fCannes à Pêche : "+(duel.canne?"§a§lActivé":"§c§lDésactivé"),
						"§8» §fArc : "+(duel.arc?"§a§lActivé":"§c§lDésactivé"),
						"§8» §fBuild : "+(duel.build?"§a§lActivé":"§c§lDésactivé"),
						"§8» §fSceaux : "+sceauxstr,
						"§8» §fGapple : "+(duel.gapple<=0?"§c§lDésactivé":"§a§l"+duel.gapple),
						"§8» §fGapple Cheat : "+(duel.gapple_cheat<=0?"§c§lDésactivé":"§a§l"+duel.gapple_cheat),
						"§8» §fPotions Bienveillantes : "+(duel.goodpotions?"§a§lActivé":"§c§lDésactivé"),
						"§8» §fPotions Malveillantes : "+(duel.badpotions?"§a§lActivé":"§c§lDésactivé"),
						"",
						"§5§lMods",
						"");
		for(Mod mod : duel.mods) {
			String color = (mod.getName().equalsIgnoreCase("normal")?"§f§l":"§5§l");
			item.addLoreLine("§8» "+color+mod.getName());
		}
		item.addLoreLine("");
		item.addLoreLine("§8§m"+GlobalsManager.linespace);
		item.addLoreLine("");
		return ClickableItem.of(item.toItemStack(), e -> {
			Player p2 = (Player)e.getWhoClicked();
			if(duel.creator.getName().equalsIgnoreCase(p2.getName())) {
				p2.closeInventory();
				p2.sendMessage(GlobalsManager.prefix+"§cVous ne pouvez pas rejoindre votre propre Duel");
				return;
			}
			if(duel.player2 != null) {
				p2.closeInventory();
				p2.sendMessage(GlobalsManager.prefix+"§cQuelqu'un a déjà rejoint ce Duel");
				return;
			}
			if(DuelsManager.getDuel(p2.getName()) != null) {
				p2.closeInventory();
				p2.sendMessage(GlobalsManager.prefix+"§cVous êtes déjà dans un Duel");
				return;
			}
			duel.player2 = p2;
			e.getWhoClicked().closeInventory();
			DuelsManager.startDuel(duel);
		});
	}
	
	private static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("unrankeds")
            .provider(new ListUnRankeds())
            .size(6, 9)
            .title("§8» §7UnRankeds")
            .build();
	
	public static void open(Player p, int page) {
		INVENTORY.open(p, page);
	}
	public static void open(Player p) {
		open(p, 0);
	}

	@Override
	public void init(Player p, InventoryContents inv) {
		List<Duel> unrankeds = DuelsManager.getEmptyUnRankedDuels();
		ClickableItem[] items = new ClickableItem[unrankeds.size()];
		for(int i = 0; i < unrankeds.size(); i++) {
			items[i] = buildItem(unrankeds.get(i), inv);
		}
		
		ItemBuilder refresh = new ItemBuilder(Material.FEATHER,1).setName("§f§lActualiser");
		
		Pagination pagination = inv.pagination();
		pagination.setItemsPerPage(9*5);
		pagination.setItems(items);
		pagination.addToIterator(inv.newIterator(SlotIterator.Type.HORIZONTAL, 0, 0));
		
		inv.set(5, 2, ClickableItem.of(MenusManager.arrow_left,
	            e -> INVENTORY.open(p, pagination.previous().getPage())));
		
		
		inv.set(5, 4, ClickableItem.of(refresh.toItemStack(), e -> {
			inv.inventory().open(p);
		}));
	    inv.set(5, 6, ClickableItem.of(MenusManager.arrow_right,
	            e -> INVENTORY.open(p, pagination.next().getPage())));
	}

	@Override
	public void update(Player p, InventoryContents inv) {}
	
	

}
