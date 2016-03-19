package com.nao20010128nao.GateZenzyo.common.network.gate_zenzyo;

public class MinecraftPacket extends DataPacket {
	public byte[] compressedData;

	public MinecraftPacket() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public byte pid() {
		// TODO 自動生成されたメソッド・スタブ
		return Info.MINECRAFT_PACKET;
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
