package fr.hunh0w.practice.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.hunh0w.practice.Practice;
import fr.hunh0w.practice.arenas.ArenasManager;
import fr.hunh0w.practice.arenas.menus.ListArenas;
import fr.hunh0w.practice.commands.ISubCommand;
import fr.hunh0w.practice.managers.GlobalsManager;
import fr.hunh0w.practice.managers.PermissionsManager;

public class CmdArena implements ISubCommand {

	@Override
	public void exec(CommandSender sender, Command cmd, String[] args) {
		Player p = (Player)sender;
		if(args.length >= 2) {
			if(args[1].equalsIgnoreCase("list")) {
				Practice.getPlayerData(p).BackInventoryClear();
				ListArenas.open(p);
				return;
			}else if(args[1].equalsIgnoreCase("delete")) {
				if(args.length < 3) {
					sender.sendMessage(GlobalsManager.prefix+"§cUtilisation : /practice arena delete <arène>");
					return;
				}
				String name = args[2];
				ArenasManager.removeArena(name);
				sender.sendMessage(GlobalsManager.prefix+"§fL'arène §c"+name+" §fa bien été §csupprimée");
				return;
			}
		}
		showHelp(sender);
	}

	@Override
	public boolean hasPermission(CommandSender sender) {
		return (PermissionsManager.hasAdminPerms(sender) || sender.hasPermission("practice.arenes"));
	}

	@Override
	public boolean onlyPlayers() {
		return true;
	}
	
	public void showHelp(CommandSender p) {
		p.sendMessage("§8§m"+GlobalsManager.linespace);
		p.sendMessage("                         §d§lAide §c§lPractice");
		p.sendMessage("§8§m"+GlobalsManager.linespace);
		p.sendMessage("");
		p.sendMessage("§8/§7practice arena list §8- §aVoir la liste des arènes");
		p.sendMessage("");
	}

}
