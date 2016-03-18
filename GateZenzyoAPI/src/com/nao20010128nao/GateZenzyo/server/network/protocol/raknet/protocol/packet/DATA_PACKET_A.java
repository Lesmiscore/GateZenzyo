package com.nao20010128nao.GateZenzyo.server.network.protocol.raknet.protocol.packet;

import com.nao20010128nao.GateZenzyo.server.network.protocol.raknet.protocol.DataPacket;
import com.nao20010128nao.GateZenzyo.server.network.protocol.raknet.protocol.Packet;

/**
 * author: MagicDroidX Nukkit Project
 */
public class DATA_PACKET_A extends DataPacket {
	public static byte ID = (byte) 0x8a;

	@Override
	public byte getID() {
		return ID;
	}

	public static final class Factory implements Packet.PacketFactory {

		@Override
		public Packet create() {
			return new DATA_PACKET_A();
		}

	}

}
