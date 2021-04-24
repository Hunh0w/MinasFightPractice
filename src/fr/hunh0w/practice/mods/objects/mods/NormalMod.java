package fr.hunh0w.practice.mods.objects.mods;

import org.bukkit.Material;

import fr.hunh0w.practice.managers.GlobalsManager;
import fr.hunh0w.practice.mods.objects.Mod;
import fr.hunh0w.practice.utils.ItemBuilder;

public class NormalMod extends Mod {

	public NormalMod() {
		super();
		setup();
	}

	@Override
	public void setup() {
		name = "Normal";
		item = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1)
				.setName("§f§lMod : §6"+name)
				.setLore("", "§8§m"+GlobalsManager.linespace, "", 
						"§8» §7Mod par défaut",
						"§8» §7Aucunes modifications",
				"", "§8§m"+GlobalsManager.linespace, "")
				.toItemStack();
	}

}
