package com.ysj.gossip;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
	public static void main(String[] args) {

		Map<String,Server> serverMap = new ConcurrentHashMap<>();
		Message message = new Message("info","topic",1L);
		for(int i=1;i<100;i++){
			Server server = new Server(String.format("192.168.1.%d",i),message);
			serverMap.put(server.getIp(),server);
			server.setMessage(message);
			server.setRoutes(serverMap);
		}
		Scanner in = new Scanner(System.in);
		while(in.hasNext()){
			String info = in.next();
			message.setInfo(info);
		}

	}
}
