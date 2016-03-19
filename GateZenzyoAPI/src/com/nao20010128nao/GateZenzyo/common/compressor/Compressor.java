package com.nao20010128nao.GateZenzyo.common.compressor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.nao20010128nao.GateZenzyo.common.network.gate_zenzyo.Info;

public abstract class Compressor {
	static Map<Byte, Compressor> instances = new HashMap<>();

	public Compressor() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public abstract byte[] compress(byte[] data) throws IOException;

	public abstract byte[] decompress(byte[] data) throws IOException;

	public abstract int getId();

	static {
		instances.put(Info.SUPPORTED_COMPRESSIONS_NONE, new NoneComressor());
		instances.put(Info.SUPPORTED_COMPRESSIONS_DEFLATE, new DeflateComressor());
		instances.put(Info.SUPPORTED_COMPRESSIONS_GZIP, new GzipComressor());
		instances.put(Info.SUPPORTED_COMPRESSIONS_LZ4, new Lz4Comressor());
	}

	public static Compressor getCompressor(byte id) {
		return instances.get(id);
	}
}
