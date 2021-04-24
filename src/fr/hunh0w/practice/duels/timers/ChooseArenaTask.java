package fr.hunh0w.practice.duels.timers;

import org.bukkit.Bukkit;

import fr.hunh0w.practice.Practice;
import fr.hunh0w.practice.duels.Duel;
import fr.hunh0w.practice.duels.DuelsManager;
import fr.hunh0w.practice.duels.menus.setup.DuelSetup;
import fr.hunh0w.practice.managers.GlobalsManager;
import fr.hunh0w.practice.utils.Title;

public class ChooseArenaTask {
	
	public int task;
	public Duel duel;
	public int index = 5;
	
	public ChooseArenaTask(Duel duel) {
		this.duel = duel;
		Practice.getPlayerData(duel.creator).arenafindtask = this;
		start();
	}
	
	public void stop() {
		Bukkit.getScheduler().cancelTask(task);
	}
	
	private void start() {
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Practice.get(), new Runnable() {
			
			@Override
			public void run() {
				if(!duel.creator.isOnline() || DuelsManager.getDuel(duel.creator.getName()) != null || DuelSetup.getMakingDuels().get(duel.creator.getName().toLowerCase()) != null || duel.arena != null) {
					Bukkit.getScheduler().cancelTask(task);
					return;
				}
				Title.sendActionBar(duel.creator, GlobalsManager.prefix+"§eRecherche d'une Arène en cours ...");
				if(index >= 5) {
					index = 0;
					
					DuelsManager.findArenaFor(duel);
					return;
				}
				index++;
			}
		}, 0, 20);
	}

}
