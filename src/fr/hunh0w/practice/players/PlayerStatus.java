package fr.hunh0w.practice.players;

public enum PlayerStatus {
	
	NONE(1, "Au Spawn"),
	INGAME(2, "In Game"),
	EDITING(3, "�dition"),
	MODERATION(4, "Mod�ration");
	
	private int id;
	private String name;
	
	PlayerStatus(int id, String name){
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	

}
