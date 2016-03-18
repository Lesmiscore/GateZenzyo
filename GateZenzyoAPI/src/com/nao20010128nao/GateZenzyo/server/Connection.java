package com.nao20010128nao.GateZenzyo.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.nao20010128nao.GateZenzyo.server.network.gate_zenzyo.ClientInfoPacket;
import com.nao20010128nao.GateZenzyo.server.network.gate_zenzyo.ConnectionHandleAcceptedPacket;
import com.nao20010128nao.GateZenzyo.server.network.gate_zenzyo.DataPacket;
import com.nao20010128nao.GateZenzyo.server.network.gate_zenzyo.HandleConnectionPacket;
import com.nao20010128nao.GateZenzyo.server.network.gate_zenzyo.ServerInfoPacket;

public class Connection {
	static final int STATUS_CLOSED = -1;
	static final int STATUS_HANDLING = 0;
	static final int STATUS_CON_OK_UNSENT = 2;
	static final int STATUS_CON_NG = 2;
	static final int STATUS_CLIENT_INFO = 3;
	static final int STATUS_SERVER_INFO = 4;
	static final int STATUS_INGAME = 5;

	InetAddress dest;
	int status = STATUS_HANDLING;
	DatagramSocket ds;
	Server server;

	String clientName, device;
	UUID clientUUID;
	byte[] supportedCompressions;
	byte sessionCompression;

	public Connection(String ip, int port, InetAddress destination, Server server)
			throws SocketException, UnknownHostException {
		dest = destination;
		ds = new DatagramSocket(new InetSocketAddress(InetAddress.getByName(ip), port));
		this.server = server;
	}

	public void process(DatagramPacket udp) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		if (status == -1) {
			server.removeEntry(this);
			return;
		}
		DataPacket dp = DataPacket.createPacketForReceived(udp.getData(), udp.getOffset(), udp.getLength());
		switch (status) {
		case STATUS_HANDLING:
			if (dp instanceof HandleConnectionPacket) {
				dp.decode();
				clientName = ((HandleConnectionPacket) dp).clientName;
				device = ((HandleConnectionPacket) dp).device;
				clientUUID = ((HandleConnectionPacket) dp).clientUUID;
				if (clientName == null || device == null || clientUUID == null) {
					status = STATUS_CON_NG;
				} else {
					status = STATUS_CON_OK_UNSENT;
				}
			} else {
				status = -1;
				throw new IllegalArgumentException("Unexpected packet: " + dp.getClass().getName());
			}
			break;
		case STATUS_CLIENT_INFO:
			if (dp instanceof ClientInfoPacket) {
				dp.decode();
				supportedCompressions = ((ClientInfoPacket) dp).supportedCompressions;

				Set<Byte> bytes = new HashSet<>();

				for (byte ib : supportedCompressions) {
					for (byte jb : Server.SUPPORTED_COMPRESSIONS) {
						if (ib == jb)
							bytes.add(ib);
					}
				}

				sessionCompression = Collections.max(bytes);

				status = STATUS_SERVER_INFO;
			} else {
				status = -1;
				throw new IllegalArgumentException("Unexpected packet: " + dp.getClass().getName());
			}
			break;
		}
	}

	public DataPacket getProcessablePacket() throws IOException {
		if (status == -1) {
			server.removeEntry(this);
			return null;
		}
		switch (status) {
		case STATUS_CON_OK_UNSENT:
			status = STATUS_CLIENT_INFO;
			return new ConnectionHandleAcceptedPacket();
		case STATUS_SERVER_INFO:
			status = STATUS_INGAME;
			ds.setSoTimeout(10);
			ServerInfoPacket sip = new ServerInfoPacket();
			sip.useCompression = sessionCompression;
			return sip;
		case STATUS_INGAME:
			DatagramPacket dp = new DatagramPacket(new byte[102400], 102400);
			ds.receive(dp);
			byte[] data = new byte[dp.getLength()];
			System.arraycopy(dp.getData(), dp.getLength(), data, 0, data.length);

		}
		return null;
	}
}
