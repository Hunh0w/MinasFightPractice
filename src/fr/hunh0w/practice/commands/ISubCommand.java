package fr.hunh0w.practice.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.hunh0w.practice.commands.subcommands.CmdArena;
import fr.hunh0w.practice.commands.subcommands.CmdBuildMode;
import fr.hunh0w.practice.commands.subcommands.CmdCreateKit;
import fr.hunh0w.practice.commands.subcommands.CmdDuels;
import fr.hunh0w.practice.commands.subcommands.CmdKits;
import fr.hunh0w.practice.commands.subcommands.CmdRemoveKit;
import fr.hunh0w.practice.commands.subcommands.CmdSetSpawn;
import fr.hunh0w.practice.commands.subcommands.CmdSetupArena;

public interface ISubCommand {
	
	public static final CmdSetupArena CmdSetupArena = new CmdSetupArena();
	public static final CmdSetSpawn CmdSetSpawn = new CmdSetSpawn();
	public static final CmdKits CmdKits = new CmdKits();
	public static final CmdBuildMode CmdBuildMode = new CmdBuildMode();
	public static final CmdArena CmdArena = new CmdArena();
	public static final CmdDuels CmdDuels = new CmdDuels();
	public static final CmdCreateKit CmdCreateKit = new CmdCreateKit();
	public static final CmdRemoveKit CmdRemoveKit = new CmdRemoveKit();
	
	public abstract void exec(CommandSender sender, Command cmd, String[] args);
	public abstract boolean hasPermission(CommandSender sender);
	public abstract boolean onlyPlayers();

}
