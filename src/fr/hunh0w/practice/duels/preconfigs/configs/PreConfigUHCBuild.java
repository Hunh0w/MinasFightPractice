package fr.hunh0w.practice.duels.preconfigs.configs;

import org.bukkit.Material;

import fr.hunh0w.practice.duels.Duel;
import fr.hunh0w.practice.duels.DuelType;
import fr.hunh0w.practice.duels.preconfigs.DuelPreConfig;
import fr.hunh0w.practice.utils.ItemBuilder;

public class PreConfigUHCBuild extends DuelPreConfig {

	public PreConfigUHCBuild(DuelType type, String name) {
		super(type, name);
		item = new ItemBuilder(Material.BRICK, 1).setName("§8> §f§l"+type.getName()).toItemStack();
	}

	@Override
	public void PreConfigure(Duel duel) {
		duel.arc = true;
		duel.badpotions = false;
		duel.goodpotions = false;
		duel.build = true;
		duel.sceaux = 3;
		duel.canne = true;
		duel.gapple = 3;
		duel.gapple_cheat = 0;
	}

}
