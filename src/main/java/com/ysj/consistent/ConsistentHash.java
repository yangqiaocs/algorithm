package com.ysj.consistent;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

public class ConsistentHash {

	static int count = 0;

	static ThreadFactory threadFactory = new ThreadFactory() {
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(new Runnable() {
				@Override
				public void run() {
					count++;
				}
			}, "ysj");
		}
	};

	private static final ExecutorService service = Executors.newCachedThreadPool(threadFactory);


	public static void main(String[] args) {
		Server A = new Server("A", "192.168.234.1", new HashMap<>());
		Server B = new Server("B", "192.168.234.2", new HashMap<>());
		Server C = new Server("C", "192.168.234.3", new HashMap<>());
		List<Server> servers = new ArrayList<>();
		servers.add(A);
		servers.add(B);
		servers.add(C);

		List<OriginClient> clients = new ArrayList<>();
		for (int i = 1; i < 100; i++) {
			clients.add(new OriginClient(String.format("192.168.220.%s", i), "C" + i));
		}

		reHash(servers,clients);

		Server D = new Server("D", "192.168.234.4", new HashMap<>());
		servers.add(D);
		reHash(servers,clients);

		servers.remove(2);
		reHash(servers,clients);
	}

	public static void reHash(List<Server> servers,List<OriginClient> clients){
		servers.forEach(server -> {
			server.setOriginIp(new HashMap<>());
		});

		servers.sort((o1, o2) -> {
			return o1.hashCode() - o2.hashCode();
		});

		List<Integer> serverIps = servers.stream().map(Server::hashCode
		).collect(Collectors.toList());

		clients.forEach(client -> {
			int clientHash = client.hashCode();
			int res = Collections.binarySearch(serverIps, clientHash);
			res = res >= 0 ? res % servers.size() : (-res - 1) % servers.size();
			servers.get(res).getOriginIp().put(client.getName(), client.getIp());
		});

		servers.forEach(server -> {
			System.out.println(server.getOriginIp().size() + " " + server.getOriginIp());
		});
		System.out.println("\n");
	}
}
