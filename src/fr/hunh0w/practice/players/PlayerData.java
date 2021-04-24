package fr.hunh0w.practice.players;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.inventory.Inventory;

import fr.hunh0w.practice.duels.timers.ChooseArenaTask;
import fr.hunh0w.practice.kits.CustomKit;
import fr.hunh0w.practice.sql.DatabaseManager;

public class PlayerData {
	
	private String username;
	private PlayerStatus state = PlayerStatus.NONE;
	
	private boolean buildmode = false;
	
	private ArrayList<CustomKit> kits = new ArrayList<>();
	
	private int elos = 0;
	private int wins = 0;
	private int fails = 0;
	private double ratio = 0.0D;
	
	private HashMap<Inventory, Inventory> backinvs = new HashMap<>();
	
	public ChooseArenaTask arenafindtask = null;
	
	public PlayerData(String username) {
		this.username = username;
	}
	
	public boolean getBuildMode() {
		return buildmode;
	}
	
	public void setBuildMode(boolean bool) {
		buildmode = bool;
	}
	
	public HashMap<Inventory, Inventory> getBackInv() {
		return backinvs;
	}
	
	public void BackInventoryClear() {
		backinvs.clear();
	}
	
	public void BackInventoryPut(Inventory inv, Inventory inv2) {
		backinvs.put(inv, inv2);
	}
	
	public void setState(PlayerStatus state) {
		this.state = state;
	}
	
	public PlayerStatus getState() {
		return this.state;
	}
	
	
	public int getWins() {
		return wins;
	}



	public void setWins(int wins) {
		this.wins = wins;
	}



	public int getFails() {
		return fails;
	}



	public void setFails(int fails) {
		this.fails = fails;
	}



	public String getUsername() {
		return username;
	}



	public double getRatio() {
		return ratio;
	}



	public int getElos() {
		return elos;
	}

	public void setElos(int elos) {
		this.elos = elos;
	}

	public ArrayList<CustomKit> getKits() {
		return kits;
	}
	
	public double calculateKDR() {
		if(fails == 0) {
			ratio = wins;
			return ratio;
		}
		if(wins == 0) {
			ratio = 0;
			return ratio;
		}
		ratio = (double)((double)wins/(double)fails);
		return ratio;
	}

	public void init() {
		try {
			Connection con = DatabaseManager.PRACTICE.getDatabase().getConnection();
			PreparedStatement ps = con.prepareStatement("INSERT IGNORE INTO users(pseudo) VALUES(?)");
			ps.setString(1, username);
			ps.executeUpdate();
			ps.close();
			refreshData(con);
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveValue(String field, int value) {
		try {
			Connection con = DatabaseManager.PRACTICE.getDatabase().getConnection();
			PreparedStatement ps = con.prepareStatement("UPDATE users SET "+field+"=? WHERE lower(pseudo)=lower(?)");
			ps.setInt(1, value);
			ps.setString(2, username);
			ps.executeUpdate();
			ps.close();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void refreshData(Connection con) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE lower(pseudo)=lower(?)");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				elos = rs.getInt("elos");
				wins = rs.getInt("wins");
				fails = rs.getInt("fails");
			}
			ps.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		calculateKDR();
	}

}
