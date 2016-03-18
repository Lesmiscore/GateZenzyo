package com.nao20010128nao.GateZenzyo.server.network.gate_zenzyo;

public class ConnectionHandleDeniedPacket extends DataPacket {

	public ConnectionHandleDeniedPacket() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public byte pid() {
		// TODO 自動生成されたメソッド・スタブ
		return Info.CONNECTION_HANDLE_DENIED;
	}

	@Override
	public void decode() {
		// TODO 自動生成されたメソッド・スタブ
		putByte(pid());
	}

	@Override
	public void encode() {
		// TODO 自動生成されたメソッド・スタブ
		getSignedByte();
	}
}
