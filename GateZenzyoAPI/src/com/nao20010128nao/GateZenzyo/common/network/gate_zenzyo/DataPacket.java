package com.nao20010128nao.GateZenzyo.common.network.gate_zenzyo;

import java.net.DatagramPacket;
import java.util.HashMap;
import java.util.Map;

public abstract class DataPacket extends com.nao20010128nao.GateZenzyo.common.network.minecraftpe.protocol.DataPacket {
	static Map<Integer, Class<? extends DataPacket>> packetClasses = new HashMap<Integer, Class<? extends DataPacket>>();
	static {
		packetClasses.put(Info.HANDLE_CONNECTION, HandleConnectionPacket.class);
		packetClasses.put(Info.CONNECTION_HANDLE_ACCEPTED, ConnectionHandleAcceptedPacket.class);
		packetClasses.put(Info.CONNECTION_HANDLE_DENIED, ConnectionHandleDeniedPacket.class);
		packetClasses.put(Info.CLIENT_INFO, ClientInfoPacket.class);
		packetClasses.put(Info.SERVER_INFO, ServerInfoPacket.class);
		packetClasses.put(Info.MINECRAFT_PACKET, MinecraftPacket.class);
	}

	public DataPacket() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void setChannel(int channel) {
	}

	@Override
	public int getChannel() {
		return 0;
	}

	public static DataPacket createPacketForReceived(byte[] buf) {
		if (buf.length == 0)
			throw new IllegalArgumentException("buf is invalid packet.");
		DataPacket dp;
		try {
			dp = packetClasses.get(buf[0]).newInstance();
		} catch (Throwable e) {
			// TODO 自動生成された catch ブロック
			throw new IllegalArgumentException("buf is invalid packet.", e);
		}
		dp.clean();
		dp.put(buf);
		dp.setOffset(0);
		return dp;
	}

	public static DataPacket createPacketForReceived(byte[] buf, int off, int len) {
		byte[] data = new byte[len];
		System.arraycopy(buf, off, data, 0, len);
		return createPacketForReceived(data);
	}

	public static DataPacket createPacketForReceived(DatagramPacket dp) {
		return createPacketForReceived(dp.getData(), dp.getOffset(), dp.getLength());
	}
}
