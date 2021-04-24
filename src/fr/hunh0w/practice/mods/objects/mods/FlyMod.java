package fr.hunh0w.practice.mods.objects.mods;

import org.bukkit.Material;

import fr.hunh0w.practice.managers.GlobalsManager;
import fr.hunh0w.practice.mods.objects.Mod;
import fr.hunh0w.practice.utils.ItemBuilder;

public class FlyMod extends Mod {
	
	public FlyMod() {
		super();
		setup();
	}

	@Override
	public void setup() {
		name = "Fly";
		item = new ItemBuilder(Material.FEATHER, 1)
				.setName("§f§lMod : §6§l"+name)
				.setLore("", "§8§m"+GlobalsManager.linespace, "", 
						"§8» §5Mod Fly !",
						"§8» §5Pour des duels volants !",
				"", "§8§m"+GlobalsManager.linespace, "")
				.toItemStack();
	}

}
