package com.nao20010128nao.GateZenzyo.common.network.gate_zenzyo;

import java.util.UUID;

public class HandleConnectionPacket extends DataPacket {
	public String clientName, device;
	public UUID clientUUID;
	public int protocolId;

	public HandleConnectionPacket() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public byte pid() {
		// TODO 自動生成されたメソッド・スタブ
		return Info.HANDLE_CONNECTION;
	}

	@Override
	public void decode() {
		// TODO 自動生成されたメソッド・スタブ
		getSignedByte();
		protocolId = getInt();
		clientName = getString();
		device = getString();
		clientUUID = getUUID();
	}

	@Override
	public void encode() {
		// TODO 自動生成されたメソッド・スタブ
		putByte(pid());
		putInt(protocolId);
		putString(clientName);
		putString(device);
		putUUID(clientUUID);
	}
}
