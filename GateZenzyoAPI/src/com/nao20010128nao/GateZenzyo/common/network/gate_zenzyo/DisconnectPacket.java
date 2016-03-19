package com.nao20010128nao.GateZenzyo.common.network.gate_zenzyo;

public class DisconnectPacket extends DataPacket {

	public DisconnectPacket() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public byte pid() {
		// TODO 自動生成されたメソッド・スタブ
		return Info.DISCONNECT_PACKET;
	}

	@Override
	public void decode() {
		// TODO 自動生成されたメソッド・スタブ
		getByte();
	}

	@Override
	public void encode() {
		// TODO 自動生成されたメソッド・スタブ
		putByte(pid());
	}
}
