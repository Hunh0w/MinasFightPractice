package fr.hunh0w.practice.listeners;


import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;

import fr.hunh0w.practice.Practice;
import fr.hunh0w.practice.duels.Duel;
import fr.hunh0w.practice.duels.DuelsManager;
import fr.hunh0w.practice.duels.menus.search.SearchManager;
import fr.hunh0w.practice.duels.menus.setup.DuelSetup;
import fr.hunh0w.practice.duels.timers.ChooseArenaTask;
import fr.hunh0w.practice.enums.Error;
import fr.hunh0w.practice.kits.setup.presetkits.PresetKitSetup;
import fr.hunh0w.practice.managers.GlobalsManager;
import fr.hunh0w.practice.managers.ScoreboardManager;
import fr.hunh0w.practice.players.PlayerData;
import fr.hunh0w.practice.players.PlayerStatus;
import fr.hunh0w.practice.utils.ItemBuilder;
import fr.hunh0w.practice.utils.TeleportUtils;
import fr.hunh0w.practice.utils.Title;

public class JoinQuitListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		e.setJoinMessage(null);
		if(GlobalsManager.spawn == null && !p.hasPermission("practice.admin") && !p.hasPermission("practice.*")) {
			p.kickPlayer("§8§m---------§c§m---------§8§m---------\n\n"+
						 "§cErreur §8(§acode : "+Error.SPAWN_NULL.getCode()+"§8)\n"+
						 "§cVeuillez contacter un §4§nAdministrateur\n\n"+
						 "§8§m---------§c§m---------§8§m---------\n\n");
			return;
		}else {
			PlayerData pd = new PlayerData(p.getName());
			Practice.PutPlayerData(p, pd);
			pd.init();
			
			TeleportUtils.TeleportDelay(p, GlobalsManager.spawn, 1);
			if(!p.hasPlayedBefore()) {
				for(Player p2 : Bukkit.getOnlinePlayers()) p2.playSound(p2.getLocation(), Sound.WITHER_SPAWN, 10, 1);
				Bukkit.broadcastMessage(GlobalsManager.prefix+"§bSouhaitez la Bienvenue à §f§l"+p.getName()+" §b!");
				Title.sendTitle(p, 15, 30, 15, "§8§lMinas§c§lFight", "§f§lBienvenue sur le §c§lPractice §f§l!");
			}
			
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
			
			giveJoin(p);
			
			for(Player p2 : Bukkit.getOnlinePlayers()) ScoreboardManager.showMainBoard(p2);
			
			p.playSound(p.getLocation(), Sound.NOTE_PLING, 10, 1);
			p.sendMessage("");
			p.sendMessage("§8§m--------"+GlobalsManager.linespace);
			p.sendMessage("");
			p.sendMessage(GlobalsManager.prefix+"§f§lBonjour §b§l"+p.getName()+" §f§l!");
			p.sendMessage("");
			p.sendMessage("§6● §eIl est conseillé de jouer en §6§l1.8.X§e.");
			p.sendMessage("§6● §eN'hésitez pas à soutenir le serveur sur discord !");
			p.sendMessage("");
			p.sendMessage("§8§m--------"+GlobalsManager.linespace);
			p.sendMessage("");
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		e.setQuitMessage(null);
		Practice.removeSetup(p);
		
		if(SearchManager.searchs.containsKey(p.getName().toLowerCase()))
			SearchManager.searchs.remove(p.getName().toLowerCase());
		if(PresetKitSetup.kits.containsKey(p.getName().toLowerCase()))
			PresetKitSetup.kits.remove(p.getName().toLowerCase());
		if(DuelSetup.getMakingDuels().containsKey(p.getName().toLowerCase()))
			DuelSetup.getMakingDuels().remove(p.getName().toLowerCase());
		
		Duel d = DuelsManager.getDuel(p.getName());
		PlayerData data = Practice.getPlayerData(p);
		if(d != null && d.player2 != null && data.getState() == PlayerStatus.INGAME) {
			data.setFails(data.getFails()+1);
			data.saveValue("fails", data.getFails());
			Player killer = d.creator;
			if(d.creator.getName().equalsIgnoreCase(p.getName())) killer = d.player2;
			Title.sendTitle(killer, 20, 45, 20, "§e§lFélicitations !", "§a§lVous avez gagné ce Duel !");
			Bukkit.broadcastMessage(GlobalsManager.prefix+"§4§l"+killer.getName()+" §7vient de gagner un duel"+(d.ranked?" §e§lClassé§7":"")+" contre §c"+p.getName());
			for(Player p2 : Bukkit.getOnlinePlayers()) {
				if(!p2.getName().equalsIgnoreCase(p.getName())) 
					p2.playSound(p2.getLocation(), "mob.guardian.curse", 10, 1);
			}
			killer.setFireTicks(0);
			killer.setMaximumNoDamageTicks(20);
			killer.getInventory().setHelmet(null);
			killer.getInventory().setChestplate(null);
			killer.getInventory().setLeggings(null);
			killer.getInventory().setBoots(null);
			killer.getInventory().clear();
			killer.setHealth(killer.getMaxHealth());
			killer.setFoodLevel(20);
			killer.setLevel(0);
			killer.setGameMode(GameMode.ADVENTURE);
			killer.teleport(GlobalsManager.spawn);
			killer.playSound(killer.getLocation(), Sound.LEVEL_UP, 10, 1);
			for(PotionEffect ef : killer.getActivePotionEffects()) killer.removePotionEffect(ef.getType());
			PlayerData data2 = Practice.getPlayerData(killer);
			data2.setWins(data2.getWins()+1);
			data2.saveValue("wins", data2.getWins());
			if(d.ranked) {
				int number = new Random().nextInt(10)+5;
				data2.setElos(data2.getElos()+number);
				data.setElos(data.getElos()-number); 
				if(data.getElos() < 0) data.setElos(0);
				data2.saveValue("elos", data2.getElos());
				data.saveValue("elos", data.getElos());
				Title.sendActionBar(killer, GlobalsManager.prefix+"§7Vous gagnez §6"+number+" §eElos §7!");
			}
			
			ScoreboardManager.showMainBoard(killer);
			d.finish();
			JoinQuitListener.giveJoin(killer);
		}else if(d != null) {
			DuelsManager.removeAll(p.getName());
		}
		if(Practice.getPlayerData(p) != null) {
			ChooseArenaTask task = Practice.getPlayerData(p).arenafindtask;
			if(task != null) task.stop();
		}
		Practice.removePlayerData(p);
		ScoreboardManager.deleteBoard(p);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Practice.get(), new Runnable() {
			
			@Override
			public void run() {
				for(Player p2 : Bukkit.getOnlinePlayers()) {
					if(p.getName().equalsIgnoreCase(p2.getName())) continue;
					ScoreboardManager.showMainBoard(p2);
				}
			}
		}, 1);
		
	}
	
	public static void giveJoin(Player p) {
		String[] lore = new String[] {"", "§8§m----------------------------------",
				   "",
				   "    §f§lDuels §e§lClassés §f§l!",
				   "",
				   "§8► §7Clique pour rejoindre un Duel §e§lClassé §7!",
				   "", 
				   "§8§m----------------------------------", ""};
		String[] lore2 = new String[] {"", "§8§m----------------------------------",
				   "",
				   "    §f§lDuels §c§lNON §e§lClassés §f§l!",
				   "",
				   "§8► §7Clique droit pour rejoindre un Duel Automatiquement !",
				   "§8► §7Clique gauche pour choisir un Duel !",
				   "", 
				   "§8§m----------------------------------", ""};

		ItemBuilder ranked = new ItemBuilder(Material.DIAMOND_SWORD, 1).setName("§8➤ §6§lRanked")
				.setLore(lore);
		ItemBuilder unranked = new ItemBuilder(Material.IRON_SWORD, 1).setName("§8➤ §7UnRanked")
				.setLore(lore2);
		ItemBuilder kits = new ItemBuilder(Material.CHEST, 1).setName("§8➤ §b§lKits")
				.setLore("", "§8§m----------------------------------",
						   "",
						   "    §f§lLes Kits",
						   "",
						   "§8► §7Cliquez pour voir tous les §b§lKits §7!",
						   "", 
						   "§8§m----------------------------------", "");
		ItemBuilder mods = new ItemBuilder(Material.NETHER_STAR, 1).setName("§8➤ §5§lMods")
				.setLore("", "§8§m----------------------------------",
						   "",
						   "    §f§lLes Mods",
						   "",
						   "§8► §7Cliquez pour voir tous vos §b§lMods §7!",
						   "", 
						   "§8§m----------------------------------", "");
		ItemBuilder duelcreator = new ItemBuilder(Material.INK_SACK, 1, (byte)10).setName("§8➤ §a§lCréer un §c§lDuel")
				.setLore("", "§8§m----------------------------------",
						   "",
						   "    §a§lDuel Creator",
						   "",
						   "§8► §7Cliquez pour créer votre propre §c§lDuel §7!",
						   "", 
						   "§8§m----------------------------------", "");
		ItemBuilder cancelduel = new ItemBuilder(Material.INK_SACK, 1, (byte)1).setName("§c§lAnnuler le Duel")
				.setLore("", "§8§m----------------------------------",
						   "",
						   "    §c§lDuel Destroyer",
						   "",
						   "§8► §7Cliquez pour supprimer votre §c§lDuel",
						   "", 
						   "§8§m----------------------------------", "");
		p.getInventory().setItem(1, unranked.toItemStack());
		p.getInventory().setItem(2, ranked.toItemStack());
		p.getInventory().setItem(4, (DuelsManager.getDuel(p.getName()) == null?duelcreator.toItemStack():cancelduel.toItemStack()));
		p.getInventory().setItem(6, kits.toItemStack());
		p.getInventory().setItem(7, mods.toItemStack());
	}

}
