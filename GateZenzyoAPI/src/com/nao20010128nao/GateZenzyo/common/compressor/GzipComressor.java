package com.nao20010128nao.GateZenzyo.common.compressor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.nao20010128nao.GateZenzyo.common.network.gate_zenzyo.Info;

public class GzipComressor extends Compressor {

	public GzipComressor() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public byte[] compress(byte[] data) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		GZIPOutputStream dos = new GZIPOutputStream(baos);
		dos.write(data);
		dos.close();
		return baos.toByteArray();
	}

	@Override
	public byte[] decompress(byte[] data) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		GZIPInputStream gis = new GZIPInputStream(bais);

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
		return Info.SUPPORTED_COMPRESSIONS_GZIP;
	}

}
