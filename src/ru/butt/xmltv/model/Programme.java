package ru.butt.xmltv.model;

import java.util.Date;

public class Programme {
	private Date start;
	private Date stop;
	private InterString title;
	private InterString desc;
	private InterString category;
	private int channelId;
	private Channel channel;
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getStop() {
		return stop;
	}
	public void setStop(Date stop) {
		this.stop = stop;
	}
	public InterString getTitle() {
		return title;
	}
	public void setTitle(InterString title) {
		this.title = title;
	}
	public InterString getDesc() {
		return desc;
	}
	public void setDesc(InterString desc) {
		this.desc = desc;
	}
	public InterString getCategory() {
		return category;
	}
	public void setCategory(InterString category) {
		this.category = category;
	}
	public int getChannelId() {
		return channelId;
	}
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	
}
