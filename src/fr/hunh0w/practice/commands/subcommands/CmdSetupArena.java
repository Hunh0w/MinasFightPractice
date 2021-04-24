package fr.hunh0w.practice.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.hunh0w.practice.Practice;
import fr.hunh0w.practice.arenas.ArenasManager;
import fr.hunh0w.practice.arenas.objects.ArenaSetup;
import fr.hunh0w.practice.commands.ISubCommand;
import fr.hunh0w.practice.managers.GlobalsManager;
import fr.hunh0w.practice.managers.PermissionsManager;
import fr.hunh0w.practice.utils.LocationBuilder;

public class CmdSetupArena implements ISubCommand {

	@Override
	public void exec(CommandSender sender, Command cmd, String[] args) {
		Player p = (Player)sender;
		if(args.length < 2) {
			showHelp(p);
			return;
		}
		String sub = args[1];
		ArenaSetup setup = Practice.getSetup(p);
		if(sub.equalsIgnoreCase("info")) {
			p.sendMessage("");
			p.sendMessage("§8§l>> §3Paramètres Actuels");
			p.sendMessage("");
			p.sendMessage(" §3Point 1 §8> §f"+(setup.location1==null?"§cNon défini":LocationBuilder.toChars(setup.location1)));
			p.sendMessage(" §3Point 2 §8> §f"+(setup.location2==null?"§cNon défini":LocationBuilder.toChars(setup.location2)));
			p.sendMessage(" §3Spawn 1 §8> §f"+(setup.spawn1==null?"§cNon défini":LocationBuilder.toChars(setup.spawn1)));
			p.sendMessage(" §3Spawn 2 §8> §f"+(setup.spawn2==null?"§cNon défini":LocationBuilder.toChars(setup.spawn2)));
			p.sendMessage(" §3Spawn Spectateur §8> §f"+(setup.spawnspec==null?"§cNon défini":LocationBuilder.toChars(setup.spawnspec)));
			p.sendMessage(" §7§oPour définir le Point 1 et 2 il faut interagir avec une plume nommée 'ARENACREATOR'");
			p.sendMessage("");
		}else if(sub.equalsIgnoreCase("clear")) {
			setup.clear();
			p.sendMessage(GlobalsManager.prefix+"§fLes paramètres ont bien été §aclear");
		}else if(sub.equalsIgnoreCase("spawn1")) {
			setup.spawn1 = p.getLocation();
			p.sendMessage(GlobalsManager.prefix+"§fLe §aSpawn 1 §fa bien été défini");
		}else if(sub.equalsIgnoreCase("spawn2")) {
			setup.spawn2 = p.getLocation();
			p.sendMessage(GlobalsManager.prefix+"§fLe §aSpawn 2 §fa bien été défini");
		}else if(sub.equalsIgnoreCase("spawnspec")) {
			setup.spawnspec = p.getLocation();
			p.sendMessage(GlobalsManager.prefix+"§fLe §aSpawn Spectateur §fa bien été défini");
		}else if(sub.equalsIgnoreCase("create")) {
			if(args.length < 3) {
				p.sendMessage(GlobalsManager.prefix+"§cUtilisation /practice setuparena create <nom>");
				return;
			}
			if(!setup.ready()) {
				p.sendMessage(GlobalsManager.prefix+"§cIl manque des paramètres pour créer l'Arène");
				return;
			}
			String name = args[2];
			if(ArenasManager.ArenaExists(name)){
				p.sendMessage(GlobalsManager.prefix+"§cUne Arène porte déjà ce nom");
				return;
			}
			ArenasManager.registerArena(setup.toArena(name));
			setup.clear();
			p.sendMessage(GlobalsManager.prefix+"§fVous venez de créer l'Arène §a"+name);
		}
	}

	@Override
	public boolean hasPermission(CommandSender sender) {
		return (sender.hasPermission("practice.setup") || PermissionsManager.hasAdminPerms(sender));
	}
	
	@Override
	public boolean onlyPlayers() {
		return true;
	}
	
	public void showHelp(Player p) {
		p.sendMessage("§8§m---------------------------------------------");
		p.sendMessage("            §d§lAide §c§lPractice");
		p.sendMessage("§8§m---------------------------------------------");
		p.sendMessage("");
		p.sendMessage("§8/§7practice setuparena info §8- §aVoir les paramètres actuels");
		p.sendMessage("§8/§7practice setuparena create <nom> §8- §aCréer l'Arène à partir des paramètres");
		p.sendMessage("§8/§7practice setuparena spawn1 §8- §aPoser le spawn Joueur 1 du duel");
		p.sendMessage("§8/§7practice setuparena spawn2 §8- §aPoser le spawn Joueur 2 du duel");
		p.sendMessage("§8/§7practice setuparena spawnspec §8- §aPoser le spawn Spectateurs du duel");
		p.sendMessage("§8/§7practice setuparena clear §8- §aClear les paramètres actuels");
		p.sendMessage("");
	}
}
