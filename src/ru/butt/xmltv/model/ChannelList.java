package ru.butt.xmltv.model;

import java.util.HashMap;
import java.util.Map;

public class ChannelList {
	private Map<String, Channel> map;

	public ChannelList() {
		map = new HashMap<String, Channel>();
	}
	
	public void add(Channel channel) {
		map.put(channel.getId(), channel);
	}

    public Channel getChannelById(String id){
        return map.get(id);
    }
}
