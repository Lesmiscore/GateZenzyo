package com.nao20010128nao.GateZenzyo.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

import com.nao20010128nao.GateZenzyo.server.network.gate_zenzyo.DataPacket;

public class Connection {
	InetAddress dest;
	int status;

	public Connection(InetAddress destination) {
		dest = destination;
	}

	public void process(DatagramPacket dp) throws IOException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public DataPacket getProcessablePacket() throws IOException {
		return null;
	}
}
