package fr.hunh0w.practice.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import fr.hunh0w.practice.Practice;
import fr.hunh0w.practice.duels.Duel;
import fr.hunh0w.practice.duels.DuelsManager;
import fr.hunh0w.practice.players.PlayerData;
import fr.hunh0w.practice.players.PlayerStatus;

public class WorldsListener implements Listener {
	
	@EventHandler
	public void onSpawn(CreatureSpawnEvent e) {
		if(e.getSpawnReason() == SpawnReason.CUSTOM) return;
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Duel duel = DuelsManager.getDuel(e.getPlayer().getName());
		if(duel != null && Practice.getPlayerData(e.getPlayer()).getState() == PlayerStatus.INGAME) {
			if(!duel.build) e.setCancelled(true);
			if(!duel.arena.getCube().contains(e.getBlock())) e.setCancelled(true);
			return;
		}
		PlayerData data = Practice.getPlayerData(e.getPlayer());
		if(data.getBuildMode()) return;
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onBucket(PlayerBucketEmptyEvent e) {
		Duel duel = DuelsManager.getDuel(e.getPlayer().getName());
		if(duel != null && Practice.getPlayerData(e.getPlayer()).getState() == PlayerStatus.INGAME) {
			if(!duel.build) e.setCancelled(true);
			if(!duel.arena.getCube().contains(e.getBlockClicked().getRelative(e.getBlockFace()))) e.setCancelled(true);
			return;
		}
		PlayerData data = Practice.getPlayerData(e.getPlayer());
		if(data.getBuildMode()) return;
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onBucket(PlayerBucketFillEvent e) {
		Duel duel = DuelsManager.getDuel(e.getPlayer().getName());
		if(duel != null && Practice.getPlayerData(e.getPlayer()).getState() == PlayerStatus.INGAME) {
			if(!duel.build) e.setCancelled(true);
			return;
		}
		
		PlayerData data = Practice.getPlayerData(e.getPlayer());
		if(data.getBuildMode()) return;
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		Duel duel = DuelsManager.getDuel(e.getPlayer().getName());
		if(duel != null && Practice.getPlayerData(e.getPlayer()).getState() == PlayerStatus.INGAME) {
			if(!duel.build) e.setCancelled(true);
			if(!duel.arena.getCube().contains(e.getBlock())) e.setCancelled(true);
			return;
		}
		PlayerData data = Practice.getPlayerData(e.getPlayer());
		if(data.getBuildMode()) return;
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onWeather(WeatherChangeEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player)e.getEntity();
			if(!p.getWorld().getName().equalsIgnoreCase("arenes"))
				e.setCancelled(true);
		}
	}

}
