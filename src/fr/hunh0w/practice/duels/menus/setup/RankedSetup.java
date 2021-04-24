package fr.hunh0w.practice.duels.menus.setup;

import org.bukkit.entity.Player;

import fr.hunh0w.practice.duels.Duel;
import fr.hunh0w.practice.mods.objects.Mods;

public class RankedSetup {
	
	public static void open(Player p) {
		Duel duel = new Duel(p, true);
		duel.mods.add(Mods.NORMAL.getContent());
		DuelSetup.getMakingDuels().put(p.getName().toLowerCase(), duel);
		ChoosePreConfigMenu.INVENTORY.open(p);
	}
}
