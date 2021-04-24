package fr.hunh0w.practice.enums;

public enum Error {
	
	SPAWN_NULL(1, "Spawn principal non défini"),
	UNKNOWN_KIT(2, "Kit Introuvable"),
	MAKINGDUEL_NOTFOUND(3, "Duel en création Introuvable");
	
	private int code;
	private String description;
	
	Error(int code, String description){
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

}
