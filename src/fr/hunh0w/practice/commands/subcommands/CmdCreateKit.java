package fr.hunh0w.practice.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.hunh0w.practice.commands.ISubCommand;
import fr.hunh0w.practice.kits.KitsManager;
import fr.hunh0w.practice.kits.PresetKit;
import fr.hunh0w.practice.kits.setup.presetkits.ChoosePresetKitType;
import fr.hunh0w.practice.kits.setup.presetkits.PresetKitSetup;
import fr.hunh0w.practice.managers.GlobalsManager;
import fr.hunh0w.practice.managers.PermissionsManager;
import fr.hunh0w.practice.utils.VarUtils;

public class CmdCreateKit implements ISubCommand {

	@Override
	public void exec(CommandSender sender, Command cmd, String[] args) {
		Player p = (Player)sender;
		if(args.length < 2) {
			p.sendMessage(GlobalsManager.prefix+"§cUtilisation : /practice createkit <nom du kit>");
			return;
		}
		String name = VarUtils.getArgs(args, 1);
		if(KitsManager.getKit(name) != null) {
			p.sendMessage(GlobalsManager.prefix+"§cUn Kit avec ce nom existe déjà : §8'§e"+name+"§8'");
			return;
		}
		PresetKit kit = new PresetKit();
		kit.setName(name);
		PresetKitSetup.kits.put(p.getName().toLowerCase(), kit);
		ChoosePresetKitType.INVENTORY.open(p);
	}

	@Override
	public boolean hasPermission(CommandSender sender) {
		return (sender.hasPermission("practice.createkit") || PermissionsManager.hasAdminPerms(sender));
	}

	@Override
	public boolean onlyPlayers() {
		return true;
	}

}
