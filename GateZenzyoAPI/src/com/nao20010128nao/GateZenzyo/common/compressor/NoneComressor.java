package com.nao20010128nao.GateZenzyo.common.compressor;

import java.io.IOException;
import java.util.Arrays;

import com.nao20010128nao.GateZenzyo.server.network.gate_zenzyo.Info;

public class NoneComressor extends Compressor {

	public NoneComressor() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public byte[] compress(byte[] data) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		return Arrays.copyOf(data, data.length);
	}

	@Override
	public byte[] decompress(byte[] data) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		return Arrays.copyOf(data, data.length);
	}

	@Override
	public int getId() {
		// TODO 自動生成されたメソッド・スタブ
		return Info.SUPPORTED_COMPRESSIONS_NONE;
	}

}
