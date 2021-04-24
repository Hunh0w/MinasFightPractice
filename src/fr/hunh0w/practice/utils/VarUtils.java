package fr.hunh0w.practice.utils;

import java.util.List;

public class VarUtils {
	
	public static Object[] toArray(List<?> list) {
		Object[] array = new Object[list.size()];
		for(int i = 0; i < list.size();i++)
			array[i] = list.get(i);
		return array;
	}
	
	public static String getArgs(String[] args, int index) {
		String str = "";
		for(int i = 0; i < args.length; i++) {
			if(i < index) continue;
			if(str.isEmpty()) str = args[i];
			else str += " "+args[i];
		}
		return str;
	}

}
