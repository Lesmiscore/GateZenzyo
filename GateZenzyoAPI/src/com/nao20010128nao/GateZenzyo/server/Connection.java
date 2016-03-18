package com.nao20010128nao.GateZenzyo.server;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class Connection {
	InetAddress dest;
	int status;

	public Connection(InetAddress destination) {
		dest = destination;
	}

	public void process(DatagramPacket dp) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
