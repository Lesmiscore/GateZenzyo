package com.nao20010128nao.GateZenzyo.common;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {
	private Utils() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public static boolean isNullString(String s) {
		return s == null || "".equals(s);
	}

	public static byte[] trimByteArray(byte[] buf, int off, int len) {
		byte[] data = new byte[len];
		System.arraycopy(buf, off, data, 0, len);
		return data;
	}

	public static boolean writeToFile(File f, String content) {
		FileWriter fw = null;
		try {
			(fw = new FileWriter(f)).write(content);
			return true;
		} catch (Throwable e) {
			return false;
		} finally {
			try {
				if (fw != null)
					fw.close();
			} catch (IOException e) {
			}
		}
	}

	public static String readWholeFile(File f) {
		FileReader fr = null;
		char[] buf = new char[8192];
		StringBuilder sb = new StringBuilder(8192);
		try {
			fr = new FileReader(f);
			while (true) {
				int r = fr.read(buf);
				if (r <= 0) {
					break;
				}
				sb.append(buf, 0, r);
			}
			return sb.toString();
		} catch (Throwable e) {
			return null;
		} finally {
			try {
				if (fr != null)
					fr.close();
			} catch (IOException e) {
			}
		}
	}
}
