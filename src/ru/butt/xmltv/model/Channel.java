package ru.butt.xmltv.model;

public class Channel {

	private String id;
	private InterString name;
	private String icon;
	private ProgrammeList programmes = new ProgrammeList();
	public String getId() {
		return id;
	}
	public InterString getName() {
		return name;
	}
	public void setName(InterString name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public ProgrammeList getProgrammes() {
		return programmes;
	}
	public void setProgrammes(ProgrammeList programmes) {
		this.programmes = programmes;
	}
	public void setId(String id) {
		this.id = id;
	}

	
}
