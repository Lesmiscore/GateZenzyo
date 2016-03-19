package com.nao20010128nao.GateZenzyo.common.network.minecraftpe.protocol.raknet.protocol.packet;

import com.nao20010128nao.GateZenzyo.common.network.minecraftpe.protocol.raknet.protocol.Packet;

/**
 * author: MagicDroidX Nukkit Project
 */
public class ADVERTISE_SYSTEM extends UNCONNECTED_PONG {
	public static byte ID = (byte) 0x1d;

	@Override
	public byte getID() {
		return ID;
	}

	public static final class Factory implements Packet.PacketFactory {

		@Override
		public Packet create() {
			return new ADVERTISE_SYSTEM();
		}

	}
}
