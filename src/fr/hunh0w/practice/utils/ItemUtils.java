package fr.hunh0w.practice.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

public class ItemUtils {
	
	public static ItemStack[] format(int type, int level) {
		ItemStack[] armor = new ItemStack[4];
		switch(type) {
		case 2:
			armor[0] = new ItemBuilder(Material.CHAINMAIL_HELMET, 1).toItemStack();
			armor[1] = new ItemBuilder(Material.CHAINMAIL_CHESTPLATE, 1).toItemStack();
			armor[2] = new ItemBuilder(Material.CHAINMAIL_LEGGINGS, 1).toItemStack();
			armor[3] = new ItemBuilder(Material.CHAINMAIL_BOOTS, 1).toItemStack();
			break;
		case 3:
			armor[0] = new ItemBuilder(Material.IRON_HELMET, 1).toItemStack();
			armor[1] = new ItemBuilder(Material.IRON_CHESTPLATE, 1).toItemStack();
			armor[2] = new ItemBuilder(Material.IRON_LEGGINGS, 1).toItemStack();
			armor[3] = new ItemBuilder(Material.IRON_BOOTS, 1).toItemStack();
			break;
		case 4:
			armor[0] = new ItemBuilder(Material.GOLD_HELMET, 1).toItemStack();
			armor[1] = new ItemBuilder(Material.GOLD_CHESTPLATE, 1).toItemStack();
			armor[2] = new ItemBuilder(Material.GOLD_LEGGINGS, 1).toItemStack();
			armor[3] = new ItemBuilder(Material.GOLD_BOOTS, 1).toItemStack();
			break;
		case 5:
			armor[0] = new ItemBuilder(Material.DIAMOND_HELMET, 1).toItemStack();
			armor[1] = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1).toItemStack();
			armor[2] = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1).toItemStack();
			armor[3] = new ItemBuilder(Material.DIAMOND_BOOTS, 1).toItemStack();
			break;
		case 1:
		default:
			armor[0] = new ItemBuilder(Material.LEATHER_HELMET, 1).toItemStack();
			armor[1] = new ItemBuilder(Material.LEATHER_CHESTPLATE, 1).toItemStack();
			armor[2] = new ItemBuilder(Material.LEATHER_LEGGINGS, 1).toItemStack();
			armor[3] = new ItemBuilder(Material.LEATHER_BOOTS, 1).toItemStack();
			break;
		}
		if(level <= 0) return armor;
		armor[0] = new ItemBuilder(armor[0]).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, level).toItemStack();
		armor[1] = new ItemBuilder(armor[1]).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, level).toItemStack();
		armor[2] = new ItemBuilder(armor[2]).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, level).toItemStack();
		armor[3] = new ItemBuilder(armor[3]).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, level).toItemStack();
		return armor;
	}
	
	public static void giveItems(Player p, ItemStack item, int amount) {
		for(int i = 0;i < amount; i++) {
			if(p.getInventory().firstEmpty() == -1) return;
			p.getInventory().addItem(item);
		}
	}
	
	public static boolean isBad(PotionType potion) {
		switch(potion) {
		case POISON:
		case SLOWNESS:
		case WEAKNESS:
			return true;
		default:
			return false;
		}
	}

}
