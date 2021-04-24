package fr.hunh0w.practice.duels.listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;

import fr.hunh0w.practice.Practice;
import fr.hunh0w.practice.duels.Duel;
import fr.hunh0w.practice.duels.DuelsManager;
import fr.hunh0w.practice.listeners.JoinQuitListener;
import fr.hunh0w.practice.managers.GlobalsManager;
import fr.hunh0w.practice.managers.ScoreboardManager;
import fr.hunh0w.practice.players.PlayerData;
import fr.hunh0w.practice.players.PlayerStatus;
import fr.hunh0w.practice.utils.Title;

public class DuelListener implements Listener {
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		PlayerData pd = Practice.getPlayerData(p);
		Duel duel = DuelsManager.getDuel(p.getName());
		if(pd.getState() == PlayerStatus.INGAME && duel != null) {
			if(!duel.canmove) {
				e.setTo(e.getFrom());
			}else if(!duel.arena.getCube().contains(e.getTo())) {
				e.setTo(e.getFrom());
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(!(e.getEntity() instanceof Player))	return;
		Player p = (Player)e.getEntity();
		Duel duel = DuelsManager.getDuel(p.getName());
		if(duel == null) return;
		if(p.getHealth() <= 0) {
			e.setCancelled(true);
			PlayerData data = Practice.getPlayerData(p);
			if(data.getState() != PlayerStatus.INGAME) return; 
			data.setFails(data.getFails()+1);
			data.saveValue("fails", data.getFails());
			
			Player killer = duel.creator;
			if(duel.creator.getName().equalsIgnoreCase(p.getName())) killer = duel.player2;
			
			Title.sendTitle(killer, 20, 45, 20, "§e§lFélicitations !", "§a§lVous avez gagné ce Duel !");
			Title.sendTitle(p, 20, 45, 20, "", "§c§lVous avez perdu ce Duel ...");
			Bukkit.broadcastMessage(GlobalsManager.prefix+"§4§l"+killer.getName()+" §7vient de gagner un duel"+(duel.ranked?" §e§lClassé§7":"")+" contre §c"+p.getName());
			for(Player p2 : Bukkit.getOnlinePlayers()) p2.playSound(p2.getLocation(), "mob.guardian.curse", 10, 1);
			killer.setFireTicks(0); p.setFireTicks(0);
			killer.setMaximumNoDamageTicks(20); p.setMaximumNoDamageTicks(20);
			killer.getInventory().setHelmet(null); p.getInventory().setHelmet(null);
			killer.getInventory().setChestplate(null); p.getInventory().setChestplate(null);
			killer.getInventory().setLeggings(null); p.getInventory().setLeggings(null);
			killer.getInventory().setBoots(null); p.getInventory().setBoots(null);
			killer.getInventory().clear(); p.getInventory().clear();
			for(PotionEffect ef : killer.getActivePotionEffects()) killer.removePotionEffect(ef.getType());
			for(PotionEffect ef : p.getActivePotionEffects()) p.removePotionEffect(ef.getType());
			killer.setHealth(killer.getMaxHealth()); p.setHealth(p.getMaxHealth());
			killer.setFoodLevel(20); p.setFoodLevel(20); 
			killer.setLevel(0); p.setLevel(0);
			killer.setGameMode(GameMode.ADVENTURE); p.setGameMode(GameMode.ADVENTURE);
			killer.teleport(GlobalsManager.spawn); p.teleport(GlobalsManager.spawn);
			killer.playSound(killer.getLocation(), Sound.LEVEL_UP, 10, 1);
			p.playSound(p.getLocation(), Sound.VILLAGER_NO, 10, 1);
			PlayerData data2 = Practice.getPlayerData(killer);
			data2.setWins(data2.getWins()+1);
			data2.saveValue("wins", data2.getWins());
			if(duel.ranked) {
				int number = new Random().nextInt(10)+5;
				data2.setElos(data2.getElos()+number);
				data.setElos(data.getElos()-number); 
				if(data.getElos() < 0) data.setElos(0);
				data2.saveValue("elos", data2.getElos());
				data.saveValue("elos", data.getElos());
				Title.sendActionBar(killer, GlobalsManager.prefix+"§7Vous §agagnez §6"+number+" §eElos §7!");
				Title.sendActionBar(p, GlobalsManager.prefix+"§7Vous §cperdez §6"+number+" §eElos §7!");
			}
			
			ScoreboardManager.showMainBoard(killer); ScoreboardManager.showMainBoard(p);
			duel.finish();
			JoinQuitListener.giveJoin(killer); JoinQuitListener.giveJoin(p);
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.getDrops().clear();
		e.setDroppedExp(0);
		Player p = e.getEntity();
		Duel duel = DuelsManager.getDuel(p.getName());
		if(duel == null) return;
		
		PlayerData data = Practice.getPlayerData(p);
		if(data.getState() != PlayerStatus.INGAME) return; 
		data.setFails(data.getFails()+1);
		data.saveValue("fails", data.getFails());
		
		Player killer = duel.creator;
		if(duel.creator.getName().equalsIgnoreCase(p.getName())) killer = duel.player2;
		
		Title.sendTitle(killer, 20, 45, 20, "§e§lFélicitations !", "§a§lVous avez gagné ce Duel !");
		Title.sendTitle(p, 20, 45, 20, "", "§c§lVous avez perdu ce Duel ...");
		Bukkit.broadcastMessage(GlobalsManager.prefix+"§4§l"+killer.getName()+" §7vient de gagner un duel"+(duel.ranked?" §e§lClassé§7":"")+" contre §c"+p.getName());
		for(Player p2 : Bukkit.getOnlinePlayers()) p2.playSound(p2.getLocation(), "mob.guardian.curse", 10, 1);
		killer.setFireTicks(0);
		killer.setMaximumNoDamageTicks(20);
		killer.getInventory().setHelmet(null);
		killer.getInventory().setChestplate(null);
		killer.getInventory().setLeggings(null);
		killer.getInventory().setBoots(null);
		killer.getInventory().clear();
		for(PotionEffect ef : killer.getActivePotionEffects()) killer.removePotionEffect(ef.getType());
		killer.setHealth(killer.getMaxHealth());
		killer.setFoodLevel(20);
		killer.setLevel(0);
		killer.setGameMode(GameMode.ADVENTURE);
		killer.teleport(GlobalsManager.spawn);
		killer.playSound(killer.getLocation(), Sound.LEVEL_UP, 10, 1);
		p.playSound(p.getLocation(), Sound.VILLAGER_NO, 10, 1);
		
		PlayerData data2 = Practice.getPlayerData(killer);
		data2.setWins(data2.getWins()+1);
		data2.saveValue("wins", data2.getWins());
		if(duel.ranked) {
			int number = new Random().nextInt(10)+5;
			data2.setElos(data2.getElos()+number);
			data.setElos(data.getElos()-number); 
			if(data.getElos() < 0) data.setElos(0);
			data2.saveValue("elos", data2.getElos());
			data.saveValue("elos", data.getElos());
			Title.sendActionBar(killer, GlobalsManager.prefix+"§7Vous §agagnez §6"+number+" §eElos §7!");
			Title.sendActionBar(p, GlobalsManager.prefix+"§7Vous §cperdez §6"+number+" §eElos §7!");
		}
		
		ScoreboardManager.showMainBoard(killer);
		duel.finish();
		JoinQuitListener.giveJoin(killer);
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Practice.get(), new Runnable() {
			
			@Override
			public void run() {
				Player p  = e.getPlayer();
				p.setMaximumNoDamageTicks(20);
				p.getInventory().setHelmet(null);
				p.getInventory().setChestplate(null);
				p.getInventory().setLeggings(null);
				p.getInventory().setBoots(null);
				p.getInventory().clear();
				for(PotionEffect ef : p.getActivePotionEffects()) p.removePotionEffect(ef.getType());
				p.setHealth(p.getMaxHealth());
				p.setFoodLevel(20);
				p.setLevel(0);
				p.setGameMode(GameMode.ADVENTURE);
				ScoreboardManager.showMainBoard(p);
				p.teleport(GlobalsManager.spawn);
				JoinQuitListener.giveJoin(p);
			}
		}, 1);
	}

}
