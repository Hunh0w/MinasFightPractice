package fr.hunh0w.practice.kits;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

import fr.hunh0w.practice.Practice;
import fr.hunh0w.practice.duels.DuelType;
import fr.hunh0w.practice.managers.GlobalsManager;
import fr.hunh0w.practice.utils.SerializationUtils;

public class PresetKit extends Kit {
	

	public PresetKit(String name, ItemStack[] armor, ItemStack[] items, DuelType type) {
		super(name, armor, items, type);
	}
	
	public PresetKit() {super();}
	
	public static void initPresetKits() {
		loadDirs();
		if(GlobalsManager.presetkitsfile.exists())
			loadPresetKits();
	}
	
	@SuppressWarnings("unchecked")
	public static void loadPresetKits() {
		Object obj = SerializationUtils.deserialize(GlobalsManager.presetkitsfile);
		if(obj != null && obj instanceof ArrayList<?>) {
			ArrayList<SerializedKit> srlzpresetskits = (ArrayList<SerializedKit>)obj;
			for(SerializedKit srlzkit : srlzpresetskits) {
				KitsManager.getPresetKits().add(srlzkit.toKit());
			}
		}
	}
	
	public static void savePresetKits() {
		SerializationUtils.serialize(GlobalsManager.presetkitsfile, KitsManager.toSerializedKits());
	}
	
	private static void loadDirs() {
		File datafolder = Practice.get().getDataFolder();
		File presetkitsfile = new File(datafolder, "presetkits.data");
		if(!datafolder.exists()) datafolder.mkdirs();
		GlobalsManager.presetkitsfile = presetkitsfile;
	}
	
	

}
