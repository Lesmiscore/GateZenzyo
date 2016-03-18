package com.nao20010128nao.GateZenzyo.server.network.minecraftpe.protocol.raknet.protocol.packet;

import com.nao20010128nao.GateZenzyo.server.network.minecraftpe.protocol.raknet.protocol.AcknowledgePacket;
import com.nao20010128nao.GateZenzyo.server.network.minecraftpe.protocol.raknet.protocol.Packet;

/**
 * author: MagicDroidX Nukkit Project
 */
public class NACK extends AcknowledgePacket {

	public static byte ID = (byte) 0xa0;

	@Override
	public byte getID() {
		return ID;
	}

	public static final class Factory implements Packet.PacketFactory {

		@Override
		public Packet create() {
			return new NACK();
		}

	}
}
