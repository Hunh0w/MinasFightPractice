package fr.hunh0w.practice.kits;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;

import fr.hunh0w.practice.Practice;
import fr.hunh0w.practice.duels.Duel;
import fr.hunh0w.practice.duels.DuelType;
import fr.hunh0w.practice.duels.DuelsManager;
import fr.hunh0w.practice.players.PlayerData;
import fr.hunh0w.practice.utils.ItemUtils;

public class KitsManager {
	
	private static ArrayList<PresetKit> kits = new ArrayList<>();
	public static ArrayList<PresetKit> getPresetKits(){return kits;}
	
	public static PresetKit getPresetKit(String KitName) {
		for(PresetKit k : kits) {
			if(k.getName().equalsIgnoreCase(KitName)) return k;
		}
		return null;
	}
	
	public static void setPresetKits(ArrayList<PresetKit> list) {
		kits = list;
	}
	
	public static PresetKit getKit(String name) {
		for(PresetKit pk : kits) {
			if(pk.getName().equalsIgnoreCase(name)) return pk;
		}
		return null;
	}
	
	public static ArrayList<SerializedKit> toSerializedKits(){
		ArrayList<SerializedKit> srlzkits = new ArrayList<>();
		for(PresetKit kit : kits) {
			srlzkits.add(new SerializedKit(kit.getName(), kit.getType(), kit.getArmor(), kit.getItems()) );
		}
		return srlzkits;
	}
	
	public static CustomKit getKit(PlayerData p, String KitName) {
		for(CustomKit k : p.getKits()) {
			if(k.getName().equalsIgnoreCase(KitName)) return k;
		}
		return null;
	}
	public static Kit getDefaultKit(Duel duel, boolean creator) {
		Kit ret = null;
		for(PresetKit kit : KitsManager.getPresetKits()) {
			if(kit.getType() != duel.type && duel.type != DuelType.CUSTOM) continue;
			if(duel.type == DuelType.CUSTOM && kit.getType() == DuelType.WARFARE) continue;
			ret = kit;
			break;
		}
		if(ret == null) {
			if(creator) {
				for(Kit kit : Practice.getPlayerData(duel.creator).getKits()) {
					if(kit.getType() != duel.type && duel.type != DuelType.CUSTOM) continue;
					if(duel.type == DuelType.CUSTOM && kit.getType() == DuelType.WARFARE) continue;
					ret = kit;
					break;
				}
			}else {
				for(Kit kit : Practice.getPlayerData(duel.player2).getKits()) {
					if(kit.getType() != duel.type && duel.type != DuelType.CUSTOM) continue;
					if(duel.type == DuelType.CUSTOM && kit.getType() == DuelType.WARFARE) continue;
					ret = kit;
					break;
				}
			}
		}
		return ret;
	}
	
	@SuppressWarnings("deprecation")
	public static void giveKit(Player p, Kit kit) {
		Duel duel = DuelsManager.getDuel(p.getName());
		if(kit == null || duel == null) return;
		if(p.getName().equalsIgnoreCase(duel.creator.getName())) duel.creator_kit = kit;
		else duel.player2_kit = kit;
		
		ItemStack[] armor = kit.getArmor();
		p.getInventory().setHelmet(armor[0]);
		p.getInventory().setChestplate(armor[1]);
		p.getInventory().setLeggings(armor[2]);
		p.getInventory().setBoots(armor[3]);
		
		ItemStack[] content = kit.getItems().clone();
		for(int i = 0; i < content.length; i++) {
			ItemStack it = content[i];
			if(it == null) continue;
			if(it.getType() == Material.GOLDEN_APPLE) {
				if(duel.gapple <= 0 && it.getData().getData() == 0) {
					content[i] = null;
					continue;
				}else if(duel.gapple_cheat <= 0 && it.getData().getData() == 1) {
					content[i] = null;
					continue;
				}
			}
			if(!duel.badpotions) {
				if(it.getType() == Material.POTION) {
					Potion pot = Potion.fromItemStack(it);
					if(ItemUtils.isBad(pot.getType())) {
						content[i] = null;
						continue;
					}
				}
			}
			if(!duel.goodpotions) {
				if(it.getType() == Material.POTION) {
					Potion pot = Potion.fromItemStack(it);
					if(!ItemUtils.isBad(pot.getType())) {
						content[i] = null;
						continue;
					}
				}
			}
			if(!duel.arc && it.getType() == Material.BOW) {
				content[i] = null;
				continue;
			}
			if(!duel.canne && it.getType() == Material.FISHING_ROD) {
				content[i] = null;
				continue;
			}
			if(it.getType() == Material.WATER_BUCKET) 
				if(duel.sceaux != 1 && duel.sceaux != 3) {
					content[i] = null;
					continue;
				}
			if(it.getType() == Material.LAVA_BUCKET)
				if(duel.sceaux != 2 && duel.sceaux != 3) {
					content[i] = null;
					continue;
				}
			if(!duel.build && it.getType().isBlock()) {
				content[i] = null;
				continue;
			}
		}
		
		p.getInventory().setContents(content);
		p.updateInventory();
		
	}

}
