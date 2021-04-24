package fr.hunh0w.practice.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.hunh0w.practice.Practice;
import fr.hunh0w.practice.commands.ISubCommand;
import fr.hunh0w.practice.duels.menus.ListRankeds;

public class CmdDuels implements ISubCommand {

	@Override
	public void exec(CommandSender sender, Command cmd, String[] args) {
		Player p = (Player)sender;
		if(args.length < 2) {
			showHelp(p);
			return;
		}
		if(args[1].equalsIgnoreCase("list")) {
			Practice.getPlayerData(p).BackInventoryClear();
			ListRankeds.open(p);
			return;
		}
	}

	@Override
	public boolean hasPermission(CommandSender sender) {
		return false;
	}

	@Override
	public boolean onlyPlayers() {
		return true;
	}
	
	public void showHelp(Player p) {
		p.sendMessage("§8§m-------------------------------------------------------------");
		p.sendMessage("                         §d§lAide §c§lPractice");
		p.sendMessage("§8§m-------------------------------------------------------------");
		p.sendMessage("");
		p.sendMessage("§8/§7practice duel list §8- §aVoir la liste des duels");
		p.sendMessage("");
	}

}
