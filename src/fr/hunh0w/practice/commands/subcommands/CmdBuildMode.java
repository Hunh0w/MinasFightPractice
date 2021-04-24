package fr.hunh0w.practice.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.hunh0w.practice.Practice;
import fr.hunh0w.practice.commands.ISubCommand;
import fr.hunh0w.practice.managers.GlobalsManager;
import fr.hunh0w.practice.players.PlayerData;

public class CmdBuildMode implements ISubCommand {

	@Override
	public void exec(CommandSender sender, Command cmd, String[] args) {
		Player p = (Player)sender;
		PlayerData data = Practice.getPlayerData(p);
		data.setBuildMode(!data.getBuildMode());
		if(data.getBuildMode()) {
			p.sendMessage(GlobalsManager.prefix+"§4§lBuild Mode §a§lActivé");
		}else {
			p.sendMessage(GlobalsManager.prefix+"§4§lBuild Mode §c§lDésactivé");
		}
	}

	@Override
	public boolean hasPermission(CommandSender p) {
		return (p.hasPermission("practice.buildmode") || p.hasPermission("practice.admin") || p.hasPermission("practice.*"));
	}

	@Override
	public boolean onlyPlayers() {
		return true;
	}

}
