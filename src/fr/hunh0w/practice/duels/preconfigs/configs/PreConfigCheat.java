package fr.hunh0w.practice.duels.preconfigs.configs;

import org.bukkit.Material;

import fr.hunh0w.practice.duels.Duel;
import fr.hunh0w.practice.duels.DuelType;
import fr.hunh0w.practice.duels.preconfigs.DuelPreConfig;
import fr.hunh0w.practice.utils.ItemBuilder;

public class PreConfigCheat extends DuelPreConfig {

	public PreConfigCheat(DuelType type, String name) {
		super(type, name);
		item = new ItemBuilder(Material.GOLDEN_APPLE, 1).setDurability((short)1).setName("§8> §f§l"+type.getName()).toItemStack();
	}

	@Override
	public void PreConfigure(Duel duel) {
		duel.arc = false;
		duel.badpotions = false;
		duel.goodpotions = true;
		duel.build = false;
		duel.sceaux = 0;
		duel.canne = false;
		duel.gapple = 0;
		duel.gapple_cheat = 320;
	}

}
