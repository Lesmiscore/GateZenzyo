package com.nao20010128nao.GateZenzyo.common.network.gate_zenzyo;

public class Info {

	public static final int HANDLE_CONNECTION = 0;
	public static final int CONNECTION_HANDLE_ACCEPTED = 1;
	public static final int CONNECTION_HANDLE_DENIED = 2;
	public static final int CLIENT_INFO = 3;
	public static final int SERVER_INFO = 4;
	public static final int MINECRAFT_PACKET = 5;
	public static final int DISCONNECT_PACKET = 6;

	/*-******************************************************-*/

	public static final byte SUPPORTED_COMPRESSIONS_NONE = 0;
	public static final byte SUPPORTED_COMPRESSIONS_GZIP = 1;
	public static final byte SUPPORTED_COMPRESSIONS_DEFLATE = 2;
	public static final byte SUPPORTED_COMPRESSIONS_LZ4 = 3;

	private Info() {
	}
}
