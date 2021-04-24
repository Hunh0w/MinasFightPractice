package fr.hunh0w.practice.sql;

public enum DatabaseManager {
	
	PRACTICE(new DatabaseCredents("127.0.0.1", "minaspractice", 3306, "minaspractice", "19821912KAKISA"));
	
	private Database database;
	
	DatabaseManager(DatabaseCredents credentials){
		database = new Database(credentials);
	}
	
	public Database getDatabase() {
		return this.database;
	}
	
	public static void initAll() {
		String users = "CREATE TABLE IF NOT EXISTS users ("+
				"id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "+
				"pseudo VARCHAR(30) UNIQUE, "+
				"elos BIGINT(15) DEFAULT 1000, "+
				"wins INT(11) DEFAULT 0, "+
				"fails INT(11) DEFAULT 0"+
		")";
		String arenas = "CREATE TABLE IF NOT EXISTS arenas ("+
				"id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "+
				"name VARCHAR(30) UNIQUE, "+
				"location1 VARCHAR(255), "+
				"location2 VARCHAR(255), "+
				"spawn1 VARCHAR(255), "+
				"spawn2 VARCHAR(255), "+
				"spawnspec VARCHAR(255)"+
		")";
		PRACTICE.getDatabase().getTablesBuilder().addQuery(users, arenas);
		for(DatabaseManager db : DatabaseManager.values()) {
			db.getDatabase().init();
		}
	}
	
	public static void closeAll() {
		for(DatabaseManager db : DatabaseManager.values()) {
			db.getDatabase().destroy();
		}
	}

}
