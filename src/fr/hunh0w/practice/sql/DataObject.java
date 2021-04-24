package fr.hunh0w.practice.sql;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class DataObject {
	
	private ArrayList<String> data = new ArrayList<>();
	
	public DataObject add(String... strings) {
		for(String str : strings) data.add(str);
		return this;
	}
	
	public void clear() {data.clear();}
	
	public ArrayList<String> getData(){
		return data;
	}
	
	public byte[] serialize() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(baos);
			for (String element : data) {
				out.writeUTF(element);
			}
			byte[] bytes = baos.toByteArray();
			return bytes;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deserialize(InputStream inp) {
		try {
			DataInputStream in = new DataInputStream(inp);
			while (in.available() > 0) {
				data.add(in.readUTF());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
