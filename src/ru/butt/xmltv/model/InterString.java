package ru.butt.xmltv.model;

import java.util.HashMap;
import java.util.Map;

public class InterString {
	private Map<String,String> map;
	public InterString(String lang,String value){
		this();
		map.put(lang, value);
	}
	public InterString(){
		map = new HashMap<String, String>();
	}
	
	public String get(String lang) {
		return map.get(lang);
	}
}
