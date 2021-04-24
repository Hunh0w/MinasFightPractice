package fr.hunh0w.practice.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.hunh0w.practice.managers.GlobalsManager;

public class CmdPractice implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command commandobj, String label, String[] args) {
		boolean isPlayer = (sender instanceof Player);
		if(args.length >= 1) {
			ISubCommand cmd = null;
			String command = args[0];
			switch(command.toLowerCase()) {
			case "setspawn":
				cmd = ISubCommand.CmdSetSpawn;
				break;
			case "kits":
				cmd = ISubCommand.CmdKits;
				break;
			case "buildmode":
				cmd = ISubCommand.CmdBuildMode;
				break;
			case "setuparena":
				cmd = ISubCommand.CmdSetupArena;
				break;
			case "arena":
				cmd = ISubCommand.CmdArena;
				break;
			case "duel":
				cmd = ISubCommand.CmdDuels;
				break;
			case "createkit":
				cmd = ISubCommand.CmdCreateKit;
				break;
			case "removekit":
				cmd = ISubCommand.CmdRemoveKit;
				break;
			default:
				showHelp(sender, commandobj);
				return false;
			}
			if(cmd == null) return false;
			if(!cmd.hasPermission(sender)) {
				sender.sendMessage(GlobalsManager.prefix+"§cVous n'avez pas la permission d'executer cette commande");
				return false;
			}
			if(cmd.onlyPlayers() && !isPlayer) {
				sender.sendMessage(GlobalsManager.prefix+"§cSeul un joueur peux executer cette commande");
				return false;
			}
			cmd.exec(sender, commandobj, args);
			return false;
		}
		showHelp(sender, commandobj);
		return false;
	}
	
	private void showHelp(CommandSender sender, Command cmd) {
		sender.sendMessage("§8§m---------------------------");
		sender.sendMessage("         §c§lLife§f§lPractice");
		sender.sendMessage("");
		
		if(ISubCommand.CmdSetSpawn.hasPermission(sender))
			sender.sendMessage("§8> §7/"+cmd.getName()+" setspawn §8- Définir le spawn principal");
		
		if(ISubCommand.CmdArena.hasPermission(sender))
			sender.sendMessage("§8> §7/"+cmd.getName()+" arena §8- Commande des arènes");
		
		if(ISubCommand.CmdSetupArena.hasPermission(sender))
			sender.sendMessage("§8> §7/"+cmd.getName()+" setuparena §8- Créer un arène");
		
		if(ISubCommand.CmdCreateKit.hasPermission(sender))
			sender.sendMessage("§8> §7/"+cmd.getName()+" createkit <nom du kit> §8- Créer un Kit Pré-Défini");
		
		if(ISubCommand.CmdBuildMode.hasPermission(sender))
			sender.sendMessage("§8> §7/"+cmd.getName()+" buildmode §8- Activer/Désactiver le Mode Build");
		
		sender.sendMessage("§8> §7/"+cmd.getName()+" kits §8- Voir tout les kits");
		
		sender.sendMessage("");
		sender.sendMessage("§8§m---------------------------");
	}

}
