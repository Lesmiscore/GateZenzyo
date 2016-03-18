package com.nao20010128nao.GateZenzyo.server.network.gate_zenzyo;

public class ClientInfoPacket extends DataPacket {
	public byte[] supportedCompressions;

	public ClientInfoPacket() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public byte pid() {
		// TODO 自動生成されたメソッド・スタブ
		return Info.CLIENT_INFO;
	}

	@Override
	public void decode() {
		// TODO 自動生成されたメソッド・スタブ
		putByte(pid());
		putShort(supportedCompressions.length);
		put(supportedCompressions);
	}

	@Override
	public void encode() {
		// TODO 自動生成されたメソッド・スタブ
		getSignedByte();
		supportedCompressions = get(getSignedShort());
	}
}
