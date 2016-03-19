package com.nao20010128nao.GateZenzyo.client;

import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.nao20010128nao.GateZenzyo.common.Utils;
import com.nao20010128nao.GateZenzyo.common.network.gate_zenzyo.Info;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class Client {
	public static final byte[] SUPPORTED_COMPRESSIONS = new byte[] { Info.SUPPORTED_COMPRESSIONS_NONE,
			Info.SUPPORTED_COMPRESSIONS_GZIP, Info.SUPPORTED_COMPRESSIONS_DEFLATE, Info.SUPPORTED_COMPRESSIONS_LZ4 };

	String[] args;
	String ip, name, device;
	int port;
	int bindPort = 20000;
	Map<InetAddress, Connection> connections = new HashMap<>();
	DatagramSocket ds = null;
	Connection con;
	InetSocketAddress sockAddr;
	UUID uuid;

	public static void main(String... args) throws SocketException {
		System.out
				.println("  ______                                 _____       _          _____ _ _            _   \r\n"
						+ " |___  /                                / ____|     | |        / ____| (_)          | |  \r\n"
						+ "    / / ___ _ __  _____   _  ___ ______| |  __  __ _| |_ ___  | |    | |_  ___ _ __ | |_ \r\n"
						+ "   / / / _ \\ '_ \\|_  / | | |/ _ \\______| | |_ |/ _` | __/ _ \\ | |    | | |/ _ \\ '_ \\| __|\r\n"
						+ "  / /_|  __/ | | |/ /| |_| | (_) |     | |__| | (_| | ||  __/ | |____| | |  __/ | | | |_ \r\n"
						+ " /_____\\___|_| |_/___|\\__, |\\___/       \\_____|\\__,_|\\__\\___|  \\_____|_|_|\\___|_| |_|\\__|\r\n"
						+ "                       __/ |                                                             \r\n"
						+ "                      |___/                                                              ");
		new Client(args);
	}

	public Client(String[] args) throws SocketException {
		this.args = args;
		OptionParser op = new OptionParser();
		op.accepts("ip").withRequiredArg();
		op.accepts("port").withRequiredArg();
		op.accepts("name").withRequiredArg();
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
		if (os.has("name")) {
			name = os.valueOf("name").toString();
		} else {
			System.err.println("name is not set!");
			System.exit(0);
		}
		if (os.has("bind-port")) {
			bindPort = new Integer(os.valueOf("bind-port").toString());
		}
		if (new File(".uuid.txt").exists()) {
			String str = Utils.readWholeFile(new File(".uuid.txt"));
			uuid = UUID.fromString(str);
		} else {
			uuid = UUID.randomUUID();
			Utils.writeToFile(new File(".uuid.txt"), uuid.toString());
		}
		device = System.getProperty("os.name") + "-" + System.getProperty("os.version") + "-"
				+ System.getProperty("os.arch") + "-" + System.getProperty("java.vendor");

		sockAddr = new InetSocketAddress(ip, port);

		System.out.println("Binding on port " + bindPort + "...");
		ds = new DatagramSocket(bindPort);

		new ServerThread().start();
		new ConnectionCheckThread().start();

		System.out.println("Connect your Minecraft:PE at: " + bindPort + "!");
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
							Connection con = new Connection(ip, port, dp.getSocketAddress(), Client.this);
							connections.put(dp.getAddress(), con);
							con.process(dp);
							System.out.println("Connected:" + dp.getSocketAddress());
						}
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			} catch (Throwable e) {
			} finally {

			}
		}
	}

	public class ConnectionCheckThread extends Thread {
		@Override
		public void run() {
			// TODO 自動生成されたメソッド・スタブ
			try {
				while (true) {
					for (Connection con : connections.values()) {
						try {
							byte[] data = con.getProcessablePacket();
							DatagramPacket udp = new DatagramPacket(data, data.length, con.dest);
							ds.send(udp);
						} catch (SocketTimeoutException ste) {
							// ignore
						} catch (Throwable e) {
							e.printStackTrace();
						}
					}
				}
			} catch (Throwable e) {
			} finally {

			}
		}
	}
}
