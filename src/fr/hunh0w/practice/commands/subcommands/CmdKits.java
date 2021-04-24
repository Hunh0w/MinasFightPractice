package fr.hunh0w.practice.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.hunh0w.practice.Practice;
import fr.hunh0w.practice.commands.ISubCommand;
import fr.hunh0w.practice.kits.menus.KitsList;

public class CmdKits implements ISubCommand {

	@Override
	public void exec(CommandSender sender, Command cmd, String[] args) {
		Player p = (Player)sender;
		Practice.getPlayerData(p).BackInventoryClear();
		KitsList.INVENTORY.open(p);
	}

	@Override
	public boolean hasPermission(CommandSender p) {
		return true;
	}

	@Override
	public boolean onlyPlayers() {
		return true;
	}

}
