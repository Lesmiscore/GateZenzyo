package com.nao20010128nao.GateZenzyo.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import com.nao20010128nao.GateZenzyo.common.network.gate_zenzyo.DataPacket;
import com.nao20010128nao.GateZenzyo.common.network.gate_zenzyo.Info;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class Server {
	public static final byte[] SUPPORTED_COMPRESSIONS = new byte[] { Info.SUPPORTED_COMPRESSIONS_NONE,
			Info.SUPPORTED_COMPRESSIONS_GZIP, Info.SUPPORTED_COMPRESSIONS_DEFLATE, Info.SUPPORTED_COMPRESSIONS_LZ4 };

	String[] args;
	String ip;
	int port = 19132;
	int bindPort = 20000;
	Map<SocketAddress, Connection> connections = new HashMap<>();
	DatagramSocket ds = null;

	public static void main(String... args) throws SocketException {
		System.out.println(
				"  ______                                 _____       _          _____                          \r\n"
						+ " |___  /                                / ____|     | |        / ____|                         \r\n"
						+ "    / / ___ _ __  _____   _  ___ ______| |  __  __ _| |_ ___  | (___   ___ _ ____   _____ _ __ \r\n"
						+ "   / / / _ \\ '_ \\|_  / | | |/ _ \\______| | |_ |/ _` | __/ _ \\  \\___ \\ / _ \\ '__\\ \\ / / _ \\ '__|\r\n"
						+ "  / /_|  __/ | | |/ /| |_| | (_) |     | |__| | (_| | ||  __/  ____) |  __/ |   \\ V /  __/ |   \r\n"
						+ " /_____\\___|_| |_/___|\\__, |\\___/       \\_____|\\__,_|\\__\\___| |_____/ \\___|_|    \\_/ \\___|_|   \r\n"
						+ "                       __/ |                                                                   \r\n"
						+ "                      |___/                                                                    ");
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
		for (Map.Entry<SocketAddress, Connection> ent : connections.entrySet()) {
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
						if (connections.containsKey(dp.getSocketAddress())) {
							connections.get(dp.getSocketAddress()).process(dp);
						} else {
							Connection con = new Connection(ip, port, dp.getSocketAddress(), Server.this);
							connections.put(dp.getSocketAddress(), con);
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
					for (Map.Entry<SocketAddress, Connection> ent : connections.entrySet()) {
						Connection c = ent.getValue();
						try {
							DataPacket dp = c.getProcessablePacket();
							dp.encode();
							byte[] data = dp.getBuffer();
							DatagramPacket udp = new DatagramPacket(data, data.length, c.dest);
							udp.setSocketAddress(ent.getKey());
							ds.send(udp);
						} catch (SocketTimeoutException ste) {
							// ignore
						} catch (IOException e) {
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
