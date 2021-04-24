package fr.hunh0w.practice.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.hunh0w.practice.commands.ISubCommand;
import fr.hunh0w.practice.managers.ConfigManager;
import fr.hunh0w.practice.managers.GlobalsManager;
import fr.hunh0w.practice.utils.LocationBuilder;

public class CmdSetSpawn implements ISubCommand {

	@Override
	public void exec(CommandSender sender, Command cmd, String[] args) {
		Player p = (Player)sender;
		GlobalsManager.spawn = p.getLocation();
		LocationBuilder.registerLocation(ConfigManager.getConfig(), GlobalsManager.spawn, "config.spawn", true);
		ConfigManager.saveConfig();
		p.sendMessage(GlobalsManager.prefix+"§fLe §aspawn §fdu serveur a bien été §adéfini");
	}

	@Override
	public boolean hasPermission(CommandSender p) {
		return (p.hasPermission("practice.setspawn") || p.hasPermission("practice.admin") || p.hasPermission("practice.*"));
	}

	@Override
	public boolean onlyPlayers() {
		return true;
	}
	
}
