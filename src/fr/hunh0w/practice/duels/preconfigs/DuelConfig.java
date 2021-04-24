package fr.hunh0w.practice.duels.preconfigs;

import fr.hunh0w.practice.duels.Duel;
import fr.hunh0w.practice.duels.DuelType;
import fr.hunh0w.practice.duels.preconfigs.configs.PreConfigCheat;
import fr.hunh0w.practice.duels.preconfigs.configs.PreConfigDebuff;
import fr.hunh0w.practice.duels.preconfigs.configs.PreConfigNoDebuff;
import fr.hunh0w.practice.duels.preconfigs.configs.PreConfigUHCBuild;
import fr.hunh0w.practice.duels.preconfigs.configs.PreConfigVanilla;
import fr.hunh0w.practice.duels.preconfigs.configs.PreConfigEmpty;

public enum DuelConfig {
	VIDE(new PreConfigEmpty(DuelType.CUSTOM, "Vide")),
	
	DEBUFF(new PreConfigDebuff(DuelType.DEBUFF, DuelType.DEBUFF.getName())),
	NODEBUFF(new PreConfigNoDebuff(DuelType.NODEBUFF, DuelType.NODEBUFF.getName())),
	
	CHEAT(new PreConfigCheat(DuelType.CHEAT, DuelType.CHEAT.getName())),
	UHCBUILD(new PreConfigUHCBuild(DuelType.UHCBUILD, DuelType.UHCBUILD.getName())),
	VANILLA(new PreConfigVanilla(DuelType.VANILLA, DuelType.VANILLA.getName()));
	
	
	
	private DuelPreConfig preconfig;
	
	DuelConfig(DuelPreConfig preconfig){
		this.preconfig = preconfig;
	}
	
	public DuelPreConfig getPreConfig() {
		return preconfig;
	}
	
	public void PreConfigure(Duel duel) {
		preconfig.PreConfigure(duel);
	}
	
	public static DuelConfig getDuelConfig(DuelType type) {
		if(type == DuelType.WARFARE) return DuelConfig.VIDE;
		for(DuelConfig dc : values()) {
			if(dc.getPreConfig().getType() == type) return dc;
		}
		return null;
	}

}
