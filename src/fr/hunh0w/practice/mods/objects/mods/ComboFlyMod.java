package fr.hunh0w.practice.mods.objects.mods;

import org.bukkit.Material;

import fr.hunh0w.practice.managers.GlobalsManager;
import fr.hunh0w.practice.mods.objects.Mod;
import fr.hunh0w.practice.utils.ItemBuilder;

public class ComboFlyMod extends Mod {
	
	public ComboFlyMod() {
		super();
		setup();
	}

	@Override
	public void setup() {
		name = "Combo-Fly";
		item = new ItemBuilder(Material.WATCH, 1)
				.setName("§f§lMod : §6§l"+name)
				.setLore("", "§8§m"+GlobalsManager.linespace, "", 
						"§8» §5Mod Combo-Fly !",
						"§8» §5Montez en l'air votre adversaire !",
				"", "§8§m"+GlobalsManager.linespace, "")
				.toItemStack();
	}

}
