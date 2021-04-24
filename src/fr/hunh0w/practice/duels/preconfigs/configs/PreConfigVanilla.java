package fr.hunh0w.practice.duels.preconfigs.configs;

import org.bukkit.Material;

import fr.hunh0w.practice.duels.Duel;
import fr.hunh0w.practice.duels.DuelType;
import fr.hunh0w.practice.duels.preconfigs.DuelPreConfig;
import fr.hunh0w.practice.utils.ItemBuilder;

public class PreConfigVanilla extends DuelPreConfig {

	public PreConfigVanilla(DuelType type, String name) {
		super(type, name);
		item = new ItemBuilder(Material.IRON_SWORD, 1).setName("§8> §f§l"+type.getName()).toItemStack();
	}

	@Override
	public void PreConfigure(Duel duel) {
		duel.arc = false;
		duel.badpotions = false;
		duel.goodpotions = false;
		duel.build = false;
		duel.sceaux = 0;
		duel.canne = false;
		duel.gapple = 3;
		duel.gapple_cheat = 0;
	}

}
