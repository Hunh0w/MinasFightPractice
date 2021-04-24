package fr.hunh0w.practice.utils;

import org.bukkit.inventory.ItemStack;

import com.shampaggon.crackshot.CSUtility;

public class CrackShotUtils {
	
	public static CSUtility CsUtility = null;
	
	public static ItemStack getWeapon(String name) {
		if(CsUtility == null) CsUtility = new CSUtility();
		return CsUtility.generateWeapon(name);
	}

}
