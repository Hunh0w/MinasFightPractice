package fr.hunh0w.practice.mods.objects.mods;

import org.bukkit.Material;

import fr.hunh0w.practice.managers.GlobalsManager;
import fr.hunh0w.practice.mods.objects.Mod;
import fr.hunh0w.practice.utils.ItemBuilder;

public class CompagnionsMod extends Mod {
	
	public CompagnionsMod() {
		super();
		setup();
	}

	@Override
	public void setup() {
		name = "Compagnions";
		item = new ItemBuilder(Material.BONE, 1)
				.setName("§f§lMod : §6§l"+name)
				.setLore("", "§8§m"+GlobalsManager.linespace, "", 
						"§8» §5Mod Compagnions !",
						"§8» §5Besoin de renforts ?",
				"", "§8§m"+GlobalsManager.linespace, "")
				.toItemStack();
	}

}
