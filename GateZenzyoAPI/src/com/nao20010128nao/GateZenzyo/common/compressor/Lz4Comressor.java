package com.nao20010128nao.GateZenzyo.common.compressor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.nao20010128nao.GateZenzyo.common.network.gate_zenzyo.Info;

import net.jpountz.lz4.LZ4BlockInputStream;
import net.jpountz.lz4.LZ4BlockOutputStream;
import net.jpountz.lz4.LZ4Factory;

public class Lz4Comressor extends Compressor {

	public Lz4Comressor() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public byte[] compress(byte[] data) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		LZ4BlockOutputStream dos = new LZ4BlockOutputStream(baos, 1 << 32,
				LZ4Factory.fastestInstance().fastCompressor());
		dos.write(data);
		dos.close();
		return baos.toByteArray();
	}

	@Override
	public byte[] decompress(byte[] data) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		LZ4BlockInputStream gis = new LZ4BlockInputStream(bais);

		byte[] buf = new byte[1024];
		int r = -1;
		while (true) {
			r = gis.read(buf);
			if (r <= 0) {
				gis.close();
				break;
			}
			baos.write(buf, 0, r);
		}

		return baos.toByteArray();
	}

	@Override
	public int getId() {
		// TODO 自動生成されたメソッド・スタブ
		return Info.SUPPORTED_COMPRESSIONS_LZ4;
	}

}
