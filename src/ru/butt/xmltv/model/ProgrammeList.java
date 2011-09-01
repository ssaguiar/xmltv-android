package ru.butt.xmltv.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProgrammeList {
	private List<Programme> programmes;
	
	public ProgrammeList(){
		programmes = new ArrayList<Programme>();
	}
	
	public void add(Programme programme) {
		programmes.add(programme);
	}
	
	public List<Programme> now() {
		return now(0);
	}
	public List<Programme> now(int n) {
		List<Programme> list = new ArrayList<Programme>();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, n);
		for(Programme programme:programmes){
			if (cal.after(programme.getStart())&& cal.before(programme.getStop())){
				list.add(programme);
			}
		}
		return list;
	}
}
