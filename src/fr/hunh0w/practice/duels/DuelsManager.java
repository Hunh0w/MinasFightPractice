package fr.hunh0w.practice.duels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import fr.hunh0w.practice.Practice;
import fr.hunh0w.practice.arenas.ArenasManager;
import fr.hunh0w.practice.arenas.objects.Arena;
import fr.hunh0w.practice.duels.menus.kits.ChooseKitMenu;
import fr.hunh0w.practice.duels.menus.search.Search;
import fr.hunh0w.practice.duels.timers.DuelTimerStart;
import fr.hunh0w.practice.kits.CustomKit;
import fr.hunh0w.practice.kits.KitsManager;
import fr.hunh0w.practice.kits.PresetKit;
import fr.hunh0w.practice.managers.GlobalsManager;
import fr.hunh0w.practice.mods.objects.Mods;
import fr.hunh0w.practice.players.PlayerData;
import fr.hunh0w.practice.players.PlayerStatus;
import fr.hunh0w.practice.utils.Title;

public class DuelsManager {
	
	private static ArrayList<Duel> duels = new ArrayList<>();
	public static ArrayList<Duel> getDuels(){return duels;}
	
	public static ArrayList<Duel> getRankedDuels(){
		ArrayList<Duel> rankeds = new ArrayList<>();
		for(Duel duel : duels) {
			if(duel.ranked) rankeds.add(duel);
		}
		return rankeds;
	}
	
	public static ArrayList<Duel> getEmptyRankedDuels(Search search){
		ArrayList<Duel> rankeds = new ArrayList<>();
		for(Duel duel : duels) {
			if(duel.player2 != null) continue;
			if(!duel.ranked || duel.type != search.type) continue;
			if(haveMods(duel, search.mods)) rankeds.add(duel);
		}
		return rankeds;
	}
	
	public static boolean haveMods(Duel duel, List<Mods> mods) {
		if(duel.mods.size() != mods.size()) return false;
		for(Mods mod : mods) {
			if(!duel.mods.contains(mod.getContent())) return false;
		}
		return true;
	}
	
	public static ArrayList<Duel> getEmptyRankedDuels(){
		ArrayList<Duel> rankeds = new ArrayList<>();
		for(Duel duel : duels) {
			if(duel.player2 != null) continue;
			if(duel.ranked) rankeds.add(duel);
		}
		return rankeds;
	}
	
	public static ArrayList<Duel> getEmptyUnRankedDuels(){
		ArrayList<Duel> rankeds = new ArrayList<>();
		for(Duel duel : duels) {
			if(duel.player2 != null) continue;
			if(!duel.ranked) rankeds.add(duel);
		}
		return rankeds;
	}
	
	public static void removeDuel(Duel duel) {
		while(duels.contains(duel))
			duels.remove(duel);
	}
	
	public static ArrayList<Duel> getUnRankedDuels(){
		ArrayList<Duel> unrankeds = new ArrayList<>();
		for(Duel duel : duels) {
			if(!duel.ranked) unrankeds.add(duel);
		}
		return unrankeds;
	}
	
	public static ArrayList<Duel> getEmptyUnRankedDuels(Search search){
		ArrayList<Duel> unrankeds = new ArrayList<>();
		for(Duel duel : duels) {
			if(duel.player2 != null) continue;
			if(duel.ranked || duel.type != search.type) continue;
			if(haveMods(duel, search.mods)) unrankeds.add(duel);
		}
		return unrankeds;
	}
	
	public static Duel getDuel(String p) {
		for(Duel duel : duels) {
			if(duel.creator.getName().equalsIgnoreCase(p)) return duel;
			if(duel.player2 != null && duel.player2.getName().equalsIgnoreCase(p)) return duel;
		}
		return null;
	}
	
	public static void removeAll(String p) {
		ArrayList<Duel> dels = new ArrayList<>();
		for(Duel duel : duels)
			if(duel.creator.getName().equalsIgnoreCase(p) || (duel.player2 != null && duel.player2.getName().equalsIgnoreCase(p)) )
				dels.add(duel);
		for(Duel duel : dels) {
			while(duels.contains(duel))
				duels.remove(duel);
		}
	}
	
	public static boolean isFree(Arena arena) {
		for(Duel duel1 : duels) {
			if(duel1.arena != null && duel1.arena.getName().equalsIgnoreCase(arena.getName())) return false;
		}
		return true;
	}
	
	public static void findArenaFor(Duel duel) {
		ArrayList<Arena> list = new ArrayList<Arena>(ArenasManager.getArenas());
		Collections.shuffle(list);
		for(Arena arena : list) {
			if(isFree(arena)) {
				duel.arena = arena;
				DuelsManager.getDuels().add(duel);
				Title.sendActionBar(duel.creator,"");
				duel.creator.sendMessage(GlobalsManager.prefix+"§fArène trouvée : §a"+duel.arena.getName());
				duel.creator.sendMessage(GlobalsManager.prefix+"§fEn attente de joueurs §f...");
				break;
			}
		}
		list.clear();
	}
	
	public static boolean haveKits(Duel duel) {
		for(PresetKit kit : KitsManager.getPresetKits()) {
			if(kit.getType() != duel.type && duel.type != DuelType.CUSTOM) continue;
			if(duel.type == DuelType.CUSTOM && kit.getType() == DuelType.WARFARE) continue;
			return true;
		}
		boolean r1 = false;
		boolean r2 = false;
		for(CustomKit kit : Practice.getPlayerData(duel.creator).getKits()) {
			if(kit.getType() != duel.type && duel.type != DuelType.CUSTOM) continue;
			if(duel.type == DuelType.CUSTOM && kit.getType() == DuelType.WARFARE) continue;
			r1 = true;
		}
		for(CustomKit kit : Practice.getPlayerData(duel.player2).getKits()) {
			if(kit.getType() != duel.type && duel.type != DuelType.CUSTOM) continue;
			if(duel.type == DuelType.CUSTOM && kit.getType() == DuelType.WARFARE) continue;
			r2 = true;
		}
		return (r1 && r2);
	}
	
	public static void startDuel(Duel duel) {
		if(!haveKits(duel)) {
			duel.creator.sendMessage(GlobalsManager.prefix+"§cLe duel ne peux pas commencer sans Kit");
			duel.player2.sendMessage(GlobalsManager.prefix+"§cLe duel ne peux pas commencer sans Kit");
			return;
		}
		Arena arena = duel.arena;
		Practice.getPlayerData(duel.creator).setState(PlayerStatus.INGAME);
		Practice.getPlayerData(duel.player2).setState(PlayerStatus.INGAME);
		duel.creator.teleport(arena.getSpawn1());
		duel.player2.teleport(arena.getSpawn2());
		clear(duel.creator);
		clear(duel.player2);
		ChooseKitMenu.INVENTORY.open(duel.creator);
		ChooseKitMenu.INVENTORY.open(duel.player2);
		duel.timestart = new DuelTimerStart(duel);
		if(duel.mods.contains(Mods.COMBOFLY.getContent())) {
			duel.creator.setMaximumNoDamageTicks(3);
			duel.player2.setMaximumNoDamageTicks(3);
		}else {
			duel.creator.setMaximumNoDamageTicks(20);
			duel.player2.setMaximumNoDamageTicks(20);
		}
		Title.sendActionBar(duel.player2, GlobalsManager.prefix+"§fVotre §cAdversaire §fchoisi un §bKit");
	}
	
	public static void clear(Player p) {
		p.getInventory().setHelmet(null);
		p.getInventory().setChestplate(null);
		p.getInventory().setLeggings(null);
		p.getInventory().setBoots(null);
		p.getInventory().clear();
		p.setHealth(p.getMaxHealth());
		p.setFoodLevel(20);
		p.setLevel(0);
		p.setGameMode(GameMode.SURVIVAL);
	}
	
	public static Duel getMaxElos(List<Duel> list, PlayerData pd) {
		Duel d = null;
		for(Duel duel : list) {
			if(duel.creator.getName().equalsIgnoreCase(pd.getUsername())) continue;
			if(duel.player2 != null) continue;
			if(d == null) d = duel;
			else {
				int elos1 = Practice.getPlayerData(d.creator).getElos();
				int elos2 = Practice.getPlayerData(duel.creator).getElos();
				if(elos2 > elos1) d = duel;
			}
		}
		return d;
	}
	
	public static Duel getMinElos(List<Duel> list, PlayerData pd) {
		Duel d = null;
		for(Duel duel : list) {
			if(duel.creator.getName().equalsIgnoreCase(pd.getUsername())) continue;
			if(duel.player2 != null) continue;
			if(d == null) d = duel;
			else {
				int elos1 = Practice.getPlayerData(d.creator).getElos();
				int elos2 = Practice.getPlayerData(duel.creator).getElos();
				if(elos2 < elos1) d = duel;
			}
		}
		return d;
	}
	
	public static Duel getDuel(List<Duel> list, PlayerData pd) {
		Duel min = getMinElos(list, pd);
		Duel max = getMaxElos(list, pd);
		for(Duel duel : list) {
			if(duel.creator.getName().equalsIgnoreCase(pd.getUsername())) continue;
			if(duel.player2 != null) continue;
			int i = Practice.getPlayerData(duel.creator).getElos();
			if(i < pd.getElos() && i > Practice.getPlayerData(min.creator).getElos()) min = duel;
			else if(i > pd.getElos() && i < Practice.getPlayerData(max.creator).getElos()) max = duel;
		}
		if(min == null && max != null) return max;
		if(max == null && min != null) return min;
		if(max == null && min == null) return null;
		int ecartmin = pd.getElos()-Practice.getPlayerData(min.creator).getElos();
		int ecartmax = Practice.getPlayerData(max.creator).getElos()-pd.getElos();
		if(ecartmin > ecartmax) return max;
		else if(ecartmin < ecartmax) return min;
		else return max;
	}

}
