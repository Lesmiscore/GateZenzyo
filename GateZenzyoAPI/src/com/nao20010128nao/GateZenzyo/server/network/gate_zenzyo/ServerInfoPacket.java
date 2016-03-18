package com.nao20010128nao.GateZenzyo.server.network.gate_zenzyo;

public class ServerInfoPacket extends DataPacket {
	public byte useCompression;

	public ServerInfoPacket() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public byte pid() {
		// TODO 自動生成されたメソッド・スタブ
		return Info.SERVER_INFO;
	}

	@Override
	public void decode() {
		// TODO 自動生成されたメソッド・スタブ
		putByte(pid());
		putByte(useCompression);
	}

	@Override
	public void encode() {
		// TODO 自動生成されたメソッド・スタブ
		getSignedByte();
		useCompression = getSignedByte();
	}
}
