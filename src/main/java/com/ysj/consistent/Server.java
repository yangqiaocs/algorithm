package com.ysj.consistent;


import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class Server {
	private String Name;
	private String Ip;
	private HashMap<String,String> originIp;

	public Server(String name, String ip, HashMap<String, String> originIp) {
		Name = name;
		Ip = ip;
		this.originIp = originIp;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getIp() {
		return Ip;
	}

	public void setIp(String ip) {
		Ip = ip;
	}

	public HashMap<String, String> getOriginIp() {
		return originIp;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Server)) return false;
		Server server = (Server) o;
		return Objects.equals(getName(), server.getName()) && Objects.equals(getIp(), server.getIp()) && Objects.equals(getOriginIp(), server.getOriginIp());
	}

	@Override
	public int hashCode() {
		int hash = Objects.hash(getName(), getIp(), getOriginIp());
		hash += hash << 13;
		hash ^= hash >> 7;
		hash += hash << 3;
		hash ^= hash >> 17;
		hash += hash << 5;
		return Math.abs(hash);
	}

	public void setOriginIp(HashMap<String, String> originIp) {
		this.originIp = originIp;
	}
}
