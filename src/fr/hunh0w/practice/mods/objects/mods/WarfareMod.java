package fr.hunh0w.practice.mods.objects.mods;

import org.bukkit.Material;

import fr.hunh0w.practice.managers.GlobalsManager;
import fr.hunh0w.practice.mods.objects.Mod;
import fr.hunh0w.practice.utils.ItemBuilder;

public class WarfareMod extends Mod {
	
	public WarfareMod() {
		super();
		setup();
	}

	@Override
	public void setup() {
		name = "Warfare";
		item = new ItemBuilder(Material.DIAMOND_HOE, 1)
				.setName("§f§lMod : §6§l"+name)
				.setLore("", "§8§m"+GlobalsManager.linespace, "", 
						"§8» §5Mod Warfare !",
						"§8» §5Pour des duels aux armes modernes !",
				"", "§8§m"+GlobalsManager.linespace, "")
				.toItemStack();
	}
	
	

}
