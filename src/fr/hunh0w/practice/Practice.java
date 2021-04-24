package fr.hunh0w.practice;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.hunh0w.practice.arenas.ArenasManager;
import fr.hunh0w.practice.arenas.menus.ListArenas;
import fr.hunh0w.practice.arenas.objects.ArenaSetup;
import fr.hunh0w.practice.commands.CmdPractice;
import fr.hunh0w.practice.duels.listeners.DuelListener;
import fr.hunh0w.practice.kits.PresetKit;
import fr.hunh0w.practice.kits.menus.KitPreview;
import fr.hunh0w.practice.listeners.JoinQuitListener;
import fr.hunh0w.practice.listeners.PlayersListener;
import fr.hunh0w.practice.listeners.SetupArenaListener;
import fr.hunh0w.practice.listeners.SpawnInteractsListener;
import fr.hunh0w.practice.listeners.WorldsListener;
import fr.hunh0w.practice.managers.ConfigManager;
import fr.hunh0w.practice.managers.GlobalsManager;
import fr.hunh0w.practice.mods.menus.ListMods;
import fr.hunh0w.practice.players.PlayerData;
import fr.hunh0w.practice.sql.DatabaseManager;

public class Practice extends JavaPlugin {
	
	private static Practice ref;
	public static Practice get() {return ref;}
	
	private static HashMap<String, PlayerData> playerdatas = new HashMap<>();
	private static HashMap<String, ArenaSetup> setups = new HashMap<>();
	
	@Override
	public void onEnable() {
		super.onEnable();
		ref = this;
		PresetKit.initPresetKits();
		ConfigManager.load();
		DatabaseManager.initAll();
		ArenasManager.loadAll();
		getServer().getPluginManager().registerEvents(new DuelListener(), this);
		getServer().getPluginManager().registerEvents(new ListArenas(), this);
		getServer().getPluginManager().registerEvents(new ListMods(), this);
		getServer().getPluginManager().registerEvents(new SetupArenaListener(), this);
		getServer().getPluginManager().registerEvents(new JoinQuitListener(), this);
		getServer().getPluginManager().registerEvents(new KitPreview(), this);
		getServer().getPluginManager().registerEvents(new GlobalsManager(), this);
		getServer().getPluginManager().registerEvents(new WorldsListener(), this);
		getServer().getPluginManager().registerEvents(new PlayersListener(), this);
		getServer().getPluginManager().registerEvents(new SpawnInteractsListener(), this);
		getCommand("practice").setExecutor(new CmdPractice());
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.kickPlayer(GlobalsManager.prefix+"§cVeuillez vous reconnecter");
		}
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		DatabaseManager.closeAll();
		PresetKit.savePresetKits();
	}
	
	public static ArenaSetup getSetup(Player p){
		ArenaSetup setup = setups.get(p.getName().toLowerCase());
		if(setup==null) {
			setup = new ArenaSetup();
			setups.put(p.getName().toLowerCase(), setup);
		}
		return setup;
	}
	
	public static void removeSetup(Player p) {
		if(setups.containsKey(p.getName().toLowerCase()))
			setups.remove(p.getName().toLowerCase());
	}
	
	
	public static PlayerData getPlayerData(Player p) {
		return playerdatas.get(p.getName().toLowerCase());
	}
	
	public static void removePlayerData(Player p) {
		if(playerdatas.containsKey(p.getName().toLowerCase()))
			playerdatas.remove(p.getName().toLowerCase());
	}
	
	public static void PutPlayerData(Player p, PlayerData data) {
		playerdatas.put(p.getName().toLowerCase(), data);
	}

}
