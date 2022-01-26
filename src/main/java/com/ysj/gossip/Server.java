package com.ysj.gossip;

import java.util.Map;
import java.util.concurrent.*;

public class Server {
	private String Ip;
	private Message message;
	private long version = 1L;
	private Map<String,Server> routes = new ConcurrentHashMap<>();
	private final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();


	public Server(String ip, Message message) {
		this.Ip = ip;
		this.message = message;
		service.scheduleAtFixedRate(()->{
			if(this.message.getVersion()!=this.version){
				System.out.println(this.message.getVersion()+" "+this.version);
				System.out.println(this.Ip+" "+message.getInfo());
				this.setVersion(this.message.getVersion());
				spread();
			}
		},3,1, TimeUnit.SECONDS);
	}

	public void setRoutes(Map<String, Server> routes) {
		this.routes = routes;
	}

	public void spread(){
		for(Server server:routes.values()){
			server.setMessage(message);
		}
	}

	public String getIp() {
		return Ip;
	}

	public void setIp(String ip) {
		Ip = ip;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
}
