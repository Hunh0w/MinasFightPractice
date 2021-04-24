package fr.hunh0w.practice.utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class KitUtils {
	
	public static final ItemStack fireresis;
	public static final ItemStack speed;
	public static final ItemStack health;
	public static final ItemStack poison;
	public static final ItemStack lenteur;
	public static final ItemStack faiblesse;
	
	static {
		fireresis = new ItemBuilder(new ItemStack(Material.POTION, 1, (short)8259)).setName("§6§lSouffle de Magma").toItemStack();
		speed = new ItemBuilder(new ItemStack(Material.POTION, 1, (short)8226)).setName("§f§lSérum de Lumière").toItemStack();
		health = new ItemBuilder(new ItemStack(Material.POTION, 1, (short)16421)).setName("§d§lElixir de Vie").toItemStack();
		poison = new ItemBuilder(new ItemStack(Material.POTION, 1, (short)16420)).setName("§2§lPoison de Sorcier").toItemStack();
		lenteur = new ItemBuilder(new ItemStack(Material.POTION, 1, (short)16426)).setName("§8§lEssence des Ténèbres").toItemStack();
		faiblesse = new ItemBuilder(new ItemStack(Material.POTION, 1, (short)16424)).setName("§7§lPotion de Guerre").toItemStack();
		
	}
	
	public static ItemStack[] getDiamondP4Armor() {
		ItemStack[] armor = new ItemStack[] {null,null,null,null};
		Map<Enchantment, Integer> enchants = new HashMap<>();
		enchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
		armor[0] = new ItemBuilder(Material.DIAMOND_HELMET, 1).setName("§bCasque du §cGuerrier")
				.addEnchantments(enchants).toItemStack();
		armor[1] = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1).setName("§bPlastron de la §cGuerre")
				.addEnchantments(enchants).toItemStack();
		armor[2] = new ItemBuilder(Material.DIAMOND_LEGGINGS, 1).setName("§bJambières de §cCombat")
				.addEnchantments(enchants).toItemStack();
		armor[3] = new ItemBuilder(Material.DIAMOND_BOOTS, 1).setName("§bBottes du §cGuerrier")
				.addEnchantments(enchants).toItemStack();
		return armor;
	}
	
	public static ItemStack[] getVanillaArmor() {
		return new ItemStack[] {
				new ItemBuilder(Material.IRON_HELMET).setName("§eAncien Casque").toItemStack(),
				new ItemBuilder(Material.IRON_CHESTPLATE).setName("§eAncien Plastron").toItemStack(),
				new ItemBuilder(Material.IRON_LEGGINGS).setName("§eAnciennes Jambières").toItemStack(),
				new ItemBuilder(Material.IRON_BOOTS).setName("§eAnciennes Bottes").toItemStack()
		};
	}
	
	public static ItemStack getStandardSword() {
		ItemBuilder item = new ItemBuilder(Material.DIAMOND_SWORD, 1).setName("§bÉpée §cMeurtrière")
				.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5)
				.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 2)
				.addUnsafeEnchantment(Enchantment.DURABILITY, 100);
		return item.toItemStack();
	}
	
	public static ItemStack getUHCSword() {
		ItemBuilder item = new ItemBuilder(Material.DIAMOND_SWORD, 1).setName("§bÉpée §cMeurtrière")
				.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5)
				.addUnsafeEnchantment(Enchantment.DURABILITY, 100);
		return item.toItemStack();
	}
	
	public static ItemStack getBow() {
		return new ItemBuilder(Material.BOW).setName("§bArc §cSanglant").addEnchant(Enchantment.ARROW_INFINITE, 1).toItemStack();
	}
	
	public static ItemStack getAxe() {
		ItemBuilder item = new ItemBuilder(Material.DIAMOND_AXE, 1).setName("§6Hache §lExceptionnelle")
				.addUnsafeEnchantment(Enchantment.DIG_SPEED, 3)
				.addUnsafeEnchantment(Enchantment.DURABILITY, 100);
		return item.toItemStack();
	}
	
	public static ItemStack getSpade() {
		ItemBuilder item =new ItemBuilder(Material.DIAMOND_SPADE, 1).setName("§6Pelle §lExceptionnelle")
				.addUnsafeEnchantment(Enchantment.DIG_SPEED, 3)
				.addUnsafeEnchantment(Enchantment.DURABILITY, 100);
		return item.toItemStack();
	}
	
	public static ItemStack getPickaxe() {
		ItemBuilder item = new ItemBuilder(Material.DIAMOND_PICKAXE, 1).setName("§6Pioche §lExceptionnelle")
				.addUnsafeEnchantment(Enchantment.DIG_SPEED, 3)
				.addUnsafeEnchantment(Enchantment.DURABILITY, 100);
		return item.toItemStack();
	}

}
