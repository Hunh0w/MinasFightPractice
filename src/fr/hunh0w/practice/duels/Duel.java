package fr.hunh0w.practice.duels;

import java.util.ArrayList;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import fr.hunh0w.practice.Practice;
import fr.hunh0w.practice.arenas.objects.Arena;
import fr.hunh0w.practice.duels.timers.DuelTimerStart;
import fr.hunh0w.practice.kits.Kit;
import fr.hunh0w.practice.mods.objects.Mod;
import fr.hunh0w.practice.players.PlayerStatus;
import fr.hunh0w.practice.utils.WorldeditManager;

public class Duel {
	
	public Arena arena = null;
	
	public Player creator = null;
	public Player player2 = null;
	
	public Kit creator_kit = null;
	public Kit player2_kit = null;
	
	public ArrayList<Mod> mods = new ArrayList<>();
	
	public DuelType type = DuelType.CUSTOM;
	
	public DuelTimerStart timestart;
	public boolean canmove = false;
	
	public boolean ranked = false;
	
	public int swordlvl = 5;
	public int swordmatter = 5;
	
	public boolean canne = false;
	public boolean badpotions = false;
	public boolean goodpotions = false;
	public boolean arc = false;
	public boolean build = false;
	public int gapple = 0;
	public int gapple_cheat = 0;
	public int sceaux = 0;
	
	public Duel(Player p, boolean ranked) {
		this.creator = p;
		this.ranked = ranked;
	}
	
	public boolean exists() {
		for(Duel d : DuelsManager.getDuels()) {
			if(d == this) return true;
		}
		return false;
	}

	public void finish() {
		if(timestart != null) timestart.stop();
		if(creator != null && creator.isOnline())Practice.getPlayerData(creator).setState(PlayerStatus.NONE);
		if(player2 != null && player2.isOnline())Practice.getPlayerData(player2).setState(PlayerStatus.NONE);
		DuelsManager.removeDuel(this);
		WorldeditManager.loadSchematic(arena);
		for(Entity ent : arena.getLocation1().getWorld().getEntities())
			if(ent.getType() == EntityType.DROPPED_ITEM) ent.remove();
		
	}
	

}
