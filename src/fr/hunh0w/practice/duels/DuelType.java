package fr.hunh0w.practice.duels;

public enum DuelType {
	
	NODEBUFF(1, "NoDebuff"),
	DEBUFF(2, "Debuff"),
	UHCBUILD(3, "UHC-Build"),
	VANILLA(4, "Vanilla"),
	CHEAT(5, "Cheat"),
	WARFARE(6, "Warfare"),
	CUSTOM(7, "Custom");
	
	private int id;
	private String name;
	
	DuelType(int id, String name){
		this.id = id;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static DuelType getDuelType(int id) {
		for(DuelType dt : values()) {
			if(dt.getId() == id) return dt;
		}
		return null;
	}
	
	public int getId() {
		return this.id;
	}
	
	public static DuelType getById(int id) {
		for(DuelType type : DuelType.values()) {
			if(type.getId() == id) return type;
		}
		return null;
	}

}
