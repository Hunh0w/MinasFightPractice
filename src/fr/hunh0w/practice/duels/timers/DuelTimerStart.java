package fr.hunh0w.practice.duels.timers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.hunh0w.practice.Practice;
import fr.hunh0w.practice.duels.Duel;
import fr.hunh0w.practice.duels.menus.kits.ChooseKitMenu;
import fr.hunh0w.practice.duels.menus.kits.ChooseMyKits;
import fr.hunh0w.practice.duels.menus.kits.ChoosePresetKits;
import fr.hunh0w.practice.kits.KitsManager;
import fr.hunh0w.practice.managers.GlobalsManager;
import fr.hunh0w.practice.utils.Title;

public class DuelTimerStart {
	
	public int task;
	public int time = Integer.valueOf(GlobalsManager.duelstart);
	public Duel duel;
	public boolean stopped = false;
	
	public DuelTimerStart(Duel duel) {
		this.duel = duel;
		start();
	}
	
	public void stop() {
		if(stopped) return;
		Bukkit.getScheduler().cancelTask(task);
		stopped = true;
	}
	
	private void closeInvs(Player p) {
		ChooseKitMenu.INVENTORY.close(p);
		ChooseMyKits.INVENTORY.close(p);
		ChoosePresetKits.INVENTORY.close(p);
	}
	
	public void start() {
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Practice.get(), new Runnable() {
			
			@Override
			public void run() {
				if(stopped) {
					Bukkit.getScheduler().cancelTask(task);
					return;
				}
				if(time == 0) {
					duel.canmove = true;
					if(duel.creator!= null && duel.creator.isOnline() && duel.creator_kit == null) {
						closeInvs(duel.creator);
						KitsManager.giveKit(duel.creator, KitsManager.getDefaultKit(duel, true));
					}
					if(duel.player2!= null && duel.player2.isOnline() && duel.player2_kit == null) {
						closeInvs(duel.player2);
						KitsManager.giveKit(duel.player2, KitsManager.getDefaultKit(duel, false));
					}
					stopped = true;
					Bukkit.getScheduler().cancelTask(task);
					return;
				}
				String msg = GlobalsManager.prefix+"§7Début dans §c"+time+" §7secondes ...";
				if(duel.creator != null && duel.creator.isOnline())
					Title.sendActionBar(duel.creator, msg);
				if(duel.player2 != null && duel.player2.isOnline())
					Title.sendActionBar(duel.player2, msg);
				time--;
			}
		}, 0, 20);
	}

}
