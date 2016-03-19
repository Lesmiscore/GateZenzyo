package com.nao20010128nao.GateZenzyo.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import com.nao20010128nao.GateZenzyo.common.Utils;
import com.nao20010128nao.GateZenzyo.common.compressor.Compressor;
import com.nao20010128nao.GateZenzyo.common.network.gate_zenzyo.ClientInfoPacket;
import com.nao20010128nao.GateZenzyo.common.network.gate_zenzyo.ConnectionHandleDeniedPacket;
import com.nao20010128nao.GateZenzyo.common.network.gate_zenzyo.DataPacket;
import com.nao20010128nao.GateZenzyo.common.network.gate_zenzyo.HandleConnectionPacket;
import com.nao20010128nao.GateZenzyo.common.network.gate_zenzyo.Info;
import com.nao20010128nao.GateZenzyo.common.network.gate_zenzyo.MinecraftPacket;
import com.nao20010128nao.GateZenzyo.common.network.gate_zenzyo.ServerInfoPacket;

public class Connection {
	static final int STATUS_CLOSED = -1;
	static final int STATUS_INGAME = 5;

	SocketAddress dest;
	int status = -1;
	DatagramSocket ds;
	Client server;

	byte sessionCompression;

	public Connection(String ip, int port, SocketAddress socketAddress, Client server) throws IOException {
		dest = socketAddress;
		ds = new DatagramSocket(new InetSocketAddress(InetAddress.getByName(ip), port));
		this.server = server;

		DatagramPacket tmp;

		HandleConnectionPacket hcp = new HandleConnectionPacket();
		hcp.clientName = server.name;
		hcp.device = server.device;
		hcp.clientUUID = server.uuid;
		hcp.encode();
		tmp = new DatagramPacket(hcp.getBuffer(), hcp.getCount());
		ds.send(tmp);

		tmp = new DatagramPacket(new byte[1000], 1000);
		ds.receive(tmp);
		DataPacket result = DataPacket.createPacketForReceived(tmp);
		if (result instanceof ConnectionHandleDeniedPacket) {
			throw new IOException("Connection rejected");
		}

		ClientInfoPacket cip = new ClientInfoPacket();
		cip.supportedCompressions = Client.SUPPORTED_COMPRESSIONS;
		cip.encode();
		tmp = new DatagramPacket(cip.getBuffer(), cip.getCount());
		ds.send(tmp);

		tmp = new DatagramPacket(new byte[1000], 1000);
		ds.receive(tmp);
		result = DataPacket.createPacketForReceived(tmp);
		if (result instanceof ServerInfoPacket) {
			sessionCompression = ((ServerInfoPacket) result).useCompression;
		} else {
			throw new IOException("Connection error");
		}

		// login is done
		status = STATUS_INGAME;
	}

	public void process(DatagramPacket udp) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		if (status == -1) {
			server.removeEntry(this);
			return;
		}
		byte[] data = Utils.getUdpPacketBody(udp);
		MinecraftPacket mp = new MinecraftPacket();
		mp.compressedData = Compressor.getCompressor(sessionCompression).compress(data);
		mp.encode();
		DatagramPacket tmp = new DatagramPacket(mp.getBuffer(), mp.getCount());
		ds.send(tmp);
	}

	public byte[] getProcessablePacket() throws IOException {
		if (status == -1) {
			server.removeEntry(this);
			return null;
		}
		DatagramPacket dp = new DatagramPacket(new byte[102400], 102400);
		ds.receive(dp);
		DataPacket mp = DataPacket.createPacketForReceived(Utils.getUdpPacketBody(dp));
		if (mp instanceof MinecraftPacket) {
			mp.decode();
			return Compressor.getCompressor(sessionCompression).decompress(((MinecraftPacket) mp).compressedData);
		}
		return null;
	}

	public void disconnect() throws IOException {
		status = STATUS_CLOSED;
		ds.send(new DatagramPacket(new byte[] { Info.DISCONNECT_PACKET }, 1));
	}
}
