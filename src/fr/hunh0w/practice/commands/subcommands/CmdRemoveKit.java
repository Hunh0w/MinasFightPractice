package fr.hunh0w.practice.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.hunh0w.practice.commands.ISubCommand;
import fr.hunh0w.practice.kits.KitsManager;
import fr.hunh0w.practice.kits.PresetKit;
import fr.hunh0w.practice.managers.GlobalsManager;
import fr.hunh0w.practice.managers.PermissionsManager;
import fr.hunh0w.practice.utils.VarUtils;

public class CmdRemoveKit implements ISubCommand {

	@Override
	public void exec(CommandSender sender, Command cmd, String[] args) {
		if(args.length < 2) {
			sender.sendMessage(GlobalsManager.prefix+"§cUtilisation : /practice removekit <nom du kit>");
			return;
		}
		String name = VarUtils.getArgs(args, 1);
		PresetKit kit = KitsManager.getKit(name);
		if(kit == null) {
			sender.sendMessage(GlobalsManager.prefix+"§cKit Introuvable");
			return;
		}
		while(KitsManager.getPresetKits().contains(kit)) 
			KitsManager.getPresetKits().remove(kit);
		PresetKit.savePresetKits();
		sender.sendMessage(GlobalsManager.prefix+"§fLe §6Kit Pré-Défini §8'§e"+name+"§8' §fa bien été §csupprimé");
	}

	@Override
	public boolean hasPermission(CommandSender sender) {
		return (sender.hasPermission("practice.removekit") || PermissionsManager.hasAdminPerms(sender));
	}

	@Override
	public boolean onlyPlayers() {
		return false;
	}

}
