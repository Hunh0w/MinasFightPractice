package fr.hunh0w.practice.mods.objects;

import fr.hunh0w.practice.mods.objects.mods.ComboFlyMod;
import fr.hunh0w.practice.mods.objects.mods.CompagnionsMod;
import fr.hunh0w.practice.mods.objects.mods.FlyMod;
import fr.hunh0w.practice.mods.objects.mods.NormalMod;
import fr.hunh0w.practice.mods.objects.mods.WarfareMod;

public enum Mods {
	
	// gratos
	NORMAL(new NormalMod()),
	WARFARE(new WarfareMod()),
	COMBOFLY(new ComboFlyMod()),
	
	// payants
	FLY(new FlyMod()),
	COMPAGNIONS(new CompagnionsMod());
	
	private Mod content;
	
	Mods(Mod content){
		this.content = content;
	}
	
	public Mod getContent() {
		return content;
	}
	
	public static Mods getMods(Mod mod) {
		for(Mods md : values()) {
			if(md.getContent() == mod) return md;
		}
		return null;
	}

}
