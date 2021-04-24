package fr.hunh0w.practice.managers;

import java.text.DecimalFormat;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.hunh0w.practice.Practice;
import fr.hunh0w.practice.players.PlayerData;
import fr.hunh0w.practice.utils.scoreboard.FastBoard;

public class ScoreboardManager {
	
	private static HashMap<String, FastBoard> boards = new HashMap<>();
	private static DecimalFormat df = new DecimalFormat("#.##");
	
	public static FastBoard getBoardInit(Player p) {
		FastBoard board = boards.get(p.getName().toLowerCase());
		if(board == null) {
			board = new FastBoard(p);
			boards.put(p.getName().toLowerCase(), board);
		}
		return board;
	}
	
	public static void showMainBoard(Player p) {
		PlayerData pd = Practice.getPlayerData(p);
		FastBoard board = getBoardInit(p);
		board.updateTitle("§8§lMinas§4§lPractice");
		board.updateLines(
						"§8§m-------------------------",
						"",
						"§eConnectés §8► §f§l"+Bukkit.getOnlinePlayers().size(),
						"§6§lÉlos §8► §e"+pd.getElos(),
						"",
						"§cVictoires §8► §e"+pd.getWins(),
						"§cDéfaites §8► §e"+pd.getFails(),
						"§eRatio §6► "+df.format(pd.calculateKDR()),
						"",
						"§8§m-------------------------");
	}
	
	public static void deleteBoard(Player p) {
		FastBoard board = boards.get(p.getName().toLowerCase());
		if(board != null) board.delete();
		boards.put(p.getName().toLowerCase(), null);
	}

}
