package com.ysj.consistent;

import java.util.Objects;
import java.util.Random;

public class OriginClient {
	private String ip;
	private String name;
	private final int randomFactor = new Random().nextInt();
	public OriginClient(String ip, String name) {
		this.ip = ip;
		this.name = name;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof OriginClient)) return false;
		OriginClient that = (OriginClient) o;
		return randomFactor == that.randomFactor && Objects.equals(getIp(), that.getIp()) && Objects.equals(getName(), that.getName());
	}

	@Override
	public int hashCode() {
		int hash = Objects.hash(getIp(), getName());
		hash += hash << 13;
		hash ^= hash >> 7;
		hash += hash << 3;
		hash ^= hash >> 17;
		hash += hash << 5;
		return Math.abs(hash);
	}
}
