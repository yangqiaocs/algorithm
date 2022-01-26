package com.ysj.gossip;

import java.io.Serializable;

public class Message implements Serializable {
	private String Info;
	private String topic;
	private long version;

	public Message(String info, String topic, long version) {
		Info = info;
		this.topic = topic;
		this.version = version;
	}

	public String getInfo() {
		return Info;
	}

	public void setInfo(String info) {
		Info = info;
		version++;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
		version++;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
}
