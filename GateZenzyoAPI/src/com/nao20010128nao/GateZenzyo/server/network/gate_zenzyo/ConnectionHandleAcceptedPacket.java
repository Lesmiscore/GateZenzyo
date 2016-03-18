package com.nao20010128nao.GateZenzyo.server.network.gate_zenzyo;

public class ConnectionHandleAcceptedPacket extends DataPacket {

	public ConnectionHandleAcceptedPacket() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public byte pid() {
		// TODO 自動生成されたメソッド・スタブ
		return Info.CONNECTION_HANDLE_ACCEPTED;
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
