package com.nao20010128nao.GateZenzyo.common.minecraft;

/**
 * author: MagicDroidX Nukkit Project
 */
public class Skin {

	public static final int SINGLE_SKIN_SIZE = 64 * 32 * 4;
	public static final int DOUBLE_SKIN_SIZE = 64 * 64 * 4;

	public static final String MODEL_STEVE = "Standard_Steve";
	public static final String MODEL_ALEX = "Standard_Alex";

	private byte[] data = new byte[SINGLE_SKIN_SIZE];
	private String model;

	public Skin(byte[] data) {
		this(data, MODEL_STEVE);
	}

	public Skin(byte[] data, String model) {
		this.setData(data);
		this.setModel(model);
	}

	public byte[] getData() {
		return data;
	}

	public String getModel() {
		return model;
	}

	public void setData(byte[] data) {
		if (data.length != SINGLE_SKIN_SIZE && data.length != DOUBLE_SKIN_SIZE) {
			throw new IllegalArgumentException("Invalid skin");
		}
		this.data = data;
	}

	public void setModel(String model) {
		if (model == null || model.trim().isEmpty()) {
			model = MODEL_STEVE;
		}

		this.model = model;
	}

	public boolean isValid() {
		return this.data.length == SINGLE_SKIN_SIZE || this.data.length == DOUBLE_SKIN_SIZE;
	}
}
