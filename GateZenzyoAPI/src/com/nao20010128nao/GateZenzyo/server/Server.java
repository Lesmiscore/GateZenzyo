package com.nao20010128nao.GateZenzyo.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class Server extends Thread {
	String[] args;
	int port = 19132;
	Map<InetAddress, Connection> connections = new HashMap<>();

	public static void main(String... args) {
		System.out.println("  ______                                 _____       _       \r\n"
				+ " |___  /                                / ____|     | |      \r\n"
				+ "    / / ___ _ __  _____   _  ___ ______| |  __  __ _| |_ ___ \r\n"
				+ "   / / / _ \\ '_ \\|_  / | | |/ _ \\______| | |_ |/ _` | __/ _ \\\r\n"
				+ "  / /_|  __/ | | |/ /| |_| | (_) |     | |__| | (_| | ||  __/\r\n"
				+ " /_____\\___|_| |_/___|\\__, |\\___/       \\_____|\\__,_|\\__\\___|\r\n"
				+ "                       __/ |                                 \r\n"
				+ "                      |___/                                  ");
		new Server(args).start();
	}

	public Server(String[] args) {
		this.args = args;
		OptionParser op = new OptionParser();
		op.accepts("port").withRequiredArg();

		OptionSet os = op.parse(args);
		if (os.has("port")) {
			port = new Integer(os.valueOf("port").toString());
		}
	}

	@Override
	public void run() {
		DatagramSocket ds = null;
		try {
			System.out.println("Binding on port " + port + "...");
			ds = new DatagramSocket(port);
			while (true) {
				DatagramPacket dp = new DatagramPacket(new byte[102400], 102400);
				ds.receive(dp);
				if (connections.containsKey(dp.getAddress())) {
					connections.get(dp.getAddress()).process(dp);
				} else {

				}
			}
		} catch (IOException e) {
		} finally {
			if (ds != null)
				ds.close();
		}
	}
}
