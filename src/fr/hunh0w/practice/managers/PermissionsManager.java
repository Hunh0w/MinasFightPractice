package fr.hunh0w.practice.managers;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.hunh0w.practice.mods.objects.Mods;

public class PermissionsManager {
	
	public static boolean hasAdminPerms(CommandSender sender) {
		if(sender.hasPermission("practice.admin") || sender.hasPermission("practice.*") || sender.hasPermission("*")) return true;
		return false;
	}
	
	public static int countModsAccess(Player p) {
		if(p.hasPermission("practice.admin") || p.hasPermission("practice.*") || p.hasPermission("practice.duels.mods.*")) return 9999;
		int access = 1;
		if(p.hasPermission("practice.duels.mods.2")) access = 2;
		if(p.hasPermission("practice.duels.mods.3")) access = 3;
		if(p.hasPermission("practice.duels.mods.4")) access = 4;
		if(p.hasPermission("practice.duels.mods.5")) access = 5;
		if(p.hasPermission("practice.duels.mods.6")) access = 6;
		if(p.hasPermission("practice.duels.mods.7")) access = 7;
		if(p.hasPermission("practice.duels.mods.8")) access = 8;
		if(p.hasPermission("practice.duels.mods.9")) access = 9;
		if(p.hasPermission("practice.duels.mods.10")) access = 10;
		return access;
	}
	
	public static boolean hasMod(Player p, Mods mod) {
		if(p.hasPermission("practice.admin") || p.hasPermission("practice.*") || p.hasPermission("practice.mods.*")) return true;
		switch(mod) {
		case COMBOFLY:
			return p.hasPermission("practice.mods.combofly");
		case COMPAGNIONS:
			return p.hasPermission("practice.mods.compagnions");
		case FLY:
			return p.hasPermission("practice.mods.fly");
		case NORMAL:
			return true;
		case WARFARE:
			return true;
		default:
			return false;
		}
	}

}
