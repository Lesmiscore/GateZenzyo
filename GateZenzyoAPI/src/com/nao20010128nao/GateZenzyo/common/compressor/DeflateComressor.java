package com.nao20010128nao.GateZenzyo.common.compressor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

import com.nao20010128nao.GateZenzyo.server.network.gate_zenzyo.Info;

public class DeflateComressor extends Compressor {

	public DeflateComressor() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public byte[] compress(byte[] data) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DeflaterOutputStream dos = new DeflaterOutputStream(baos);
		dos.write(data);
		dos.close();
		return baos.toByteArray();
	}

	@Override
	public byte[] decompress(byte[] data) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InflaterOutputStream ios = new InflaterOutputStream(baos);
		ios.write(data);
		ios.close();
		return baos.toByteArray();
	}

	@Override
	public int getId() {
		// TODO 自動生成されたメソッド・スタブ
		return Info.SUPPORTED_COMPRESSIONS_DEFLATE;
	}

}
