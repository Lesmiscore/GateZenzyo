package com.nao20010128nao.GateZenzyo.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

import com.nao20010128nao.GateZenzyo.server.network.gate_zenzyo.DataPacket;
import com.nao20010128nao.GateZenzyo.server.network.gate_zenzyo.Info;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class Server {
	public static final byte[] SUPPORTED_COMPRESSIONS = new byte[] { Info.SUPPORTED_COMPRESSIONS_NONE,
			Info.SUPPORTED_COMPRESSIONS_GZIP, Info.SUPPORTED_COMPRESSIONS_DEFLATE };

	String[] args;
	String ip;
	int port;
	int bindPort = 19132;
	Map<InetAddress, Connection> connections = new HashMap<>();
	DatagramSocket ds = null;

	public static void main(String... args) throws SocketException {
		System.out.println("  ______                                 _____       _       \r\n"
				+ " |___  /                                / ____|     | |      \r\n"
				+ "    / / ___ _ __  _____   _  ___ ______| |  __  __ _| |_ ___ \r\n"
				+ "   / / / _ \\ '_ \\|_  / | | |/ _ \\______| | |_ |/ _` | __/ _ \\\r\n"
				+ "  / /_|  __/ | | |/ /| |_| | (_) |     | |__| | (_| | ||  __/\r\n"
				+ " /_____\\___|_| |_/___|\\__, |\\___/       \\_____|\\__,_|\\__\\___|\r\n"
				+ "                       __/ |                                 \r\n"
				+ "                      |___/                                  ");
		new Server(args);
	}

	public Server(String[] args) throws SocketException {
		this.args = args;
		OptionParser op = new OptionParser();
		op.accepts("ip").withRequiredArg();
		op.accepts("port").withRequiredArg();
		op.accepts("bind-port").withRequiredArg();

		OptionSet os = op.parse(args);
		if (os.has("ip")) {
			ip = os.valueOf("ip").toString();
		} else {
			System.err.println("ip is not set!");
			System.exit(0);
		}
		if (os.has("port")) {
			port = new Integer(os.valueOf("port").toString());
		} else {
			System.err.println("port is not set!");
			System.exit(0);
		}
		if (os.has("bind-port")) {
			bindPort = new Integer(os.valueOf("bind-port").toString());
		}

		System.out.println("Binding on port " + bindPort + "...");
		ds = new DatagramSocket(bindPort);

		new ServerThread().start();
		new ConnectionCheckThread().start();
	}

	public void removeEntry(Connection con) {
		for (Map.Entry<InetAddress, Connection> ent : connections.entrySet()) {
			if (ent.getValue() == con) {
				connections.remove(ent.getKey());
				return;
			}
		}
	}

	public class ServerThread extends Thread {
		@Override
		public void run() {
			try {
				while (true) {
					try {
						DatagramPacket dp = new DatagramPacket(new byte[102400], 102400);
						ds.receive(dp);
						if (connections.containsKey(dp.getAddress())) {
							connections.get(dp.getAddress()).process(dp);
						} else {
							Connection con = new Connection(ip, bindPort, dp.getSocketAddress(), Server.this);
							connections.put(dp.getAddress(), con);
							con.process(dp);
						}
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			} catch (Throwable e) {
			} finally {
				if (ds != null)
					ds.close();
			}
		}
	}

	public class ConnectionCheckThread extends Thread {
		@Override
		public void run() {
			// TODO 自動生成されたメソッド・スタブ
			try {
				while (true) {
					for (Connection c : connections.values()) {
						try {
							DataPacket dp = c.getProcessablePacket();
							dp.encode();
							byte[] data = dp.getBuffer();
							DatagramPacket udp = new DatagramPacket(data, data.length, c.dest);
							ds.send(udp);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} catch (Throwable e) {
			} finally {
				if (ds != null)
					ds.close();
			}
		}
	}
}
