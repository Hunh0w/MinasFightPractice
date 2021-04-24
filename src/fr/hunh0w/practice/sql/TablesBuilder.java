package fr.hunh0w.practice.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;

public class TablesBuilder {
	
	private ArrayList<String> requests = new ArrayList<>();
	
	public TablesBuilder(String... queries) {
		requests.addAll(Arrays.asList(queries));
	}
	
	public void addQuery(String... query) {
		for(String str : query)
			requests.add(str);
	}
	
	public void execute(Connection con) {
		if(requests.isEmpty()) return;
		try {
			PreparedStatement ps;
			for(String req : requests) {
				ps = con.prepareStatement(req);
				ps.executeUpdate();
			}
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
