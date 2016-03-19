package com.nao20010128nao.GateZenzyo.common.network.minecraftpe.protocol.raknet.protocol.packet;

import com.nao20010128nao.GateZenzyo.common.network.minecraftpe.protocol.raknet.protocol.DataPacket;
import com.nao20010128nao.GateZenzyo.common.network.minecraftpe.protocol.raknet.protocol.Packet;

/**
 * author: MagicDroidX Nukkit Project
 */
public class DATA_PACKET_F extends DataPacket {
	public static byte ID = (byte) 0x8f;

	@Override
	public byte getID() {
		return ID;
	}

	public static final class Factory implements Packet.PacketFactory {

		@Override
		public Packet create() {
			return new DATA_PACKET_F();
		}

	}

}
